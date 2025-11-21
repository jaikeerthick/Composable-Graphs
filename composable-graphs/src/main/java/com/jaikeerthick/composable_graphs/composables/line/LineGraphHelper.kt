package com.jaikeerthick.composable_graphs.composables.line

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.util.GraphHelper
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.util.logDebug
import kotlin.math.ceil
import kotlin.math.roundToInt

internal data class LineGraphMetrics(
    val yAxisPadding: Dp,
    val gridHeight: Float,
    val gridWidth: Float,
    val verticalStep: Float,
    val xItemSpacing: Float,
    val yItemSpacing: Float,
    val maxPointsSize: Int,
    val yAxisData: List<Number>,
    val xAxisData: List<String>,
    val yAxisLabels: List<String>,
    val offsetList: List<Offset>,
)

internal class LineGraphHelper(
    private val scope: DrawScope,
    private val style: LineGraphStyle,
    private val yAxisData: List<Number>,
    private val xAxisData: List<String>,
) {

    internal val metrics = scope.buildMetrics()
    /**
     *
     */
    private fun DrawScope.buildMetrics(): LineGraphMetrics {

        val yAxisPadding: Dp = if (style.visibility.isYAxisLabelVisible) 20.dp else 0.dp
        val paddingBottom: Dp = if (style.visibility.isXAxisLabelVisible) 20.dp else 0.dp

        val offsetList = mutableListOf<Offset>()

        /**
         * xItemSpacing, yItemSpacing => space between each item that lies on the x and y axis
         * (size.width - 16.dp.toPx())
         *               ~~~~~~~~~~~~~ => padding saved for the end of the axis
         */

        val gridHeight = (size.height) - paddingBottom.toPx()
        val gridWidth = if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width - yAxisPadding.toPx() else size.width

        // the maximum points for x and y axis to plot (maintain uniformity)
        val maxPointsSize: Int = yAxisData.size

        // maximum of the y data list
        val absMaxY = GraphHelper.getAbsoluteMax(yAxisData)
        val absMinY = 0

        val distinctYSize = yAxisData.distinct().size
        // prevent duplication of y labels when yAxisData list has same values
        // distinctYSize != 1 prevents having 0 vertical steps when every y data is 0
        val numberOfVerticalSteps =
            if (distinctYSize != 1 && yAxisData.contains(0))
                distinctYSize - 1
            else
                distinctYSize
        // prevent a vertical step of 0 which would duplicate the 0 label
        val verticalStep =
            if (absMaxY == 0)
                1F
            else
                ceil(absMaxY.toDouble()).toInt() / numberOfVerticalSteps.toFloat()

        // generate y axis label
        val yAxisLabelList = mutableListOf<String>()

        for (i in 0..numberOfVerticalSteps) {
            val intervalValue = (verticalStep*i).roundToInt()
            logDebug(message = "interval - $intervalValue")
            yAxisLabelList.add(intervalValue.toString())
        }


        val xItemSpacing = if (style.yAxisLabelPosition == LabelPosition.RIGHT){
            gridWidth / (maxPointsSize - 1)
        } else (gridWidth - yAxisPadding.toPx()) / (maxPointsSize - 1)
        val yItemSpacing = gridHeight / (yAxisLabelList.size - 1)

        offsetList.clear()  // clearing list to avoid data duplication during recomposition
        for (i in 0 until maxPointsSize) {
            //val pos = if (style.yAxisLabelPosition == LabelPosition.RIGHT) i else (i+1)
            //val x1 = xItemSpacing * pos
            val x = if (style.yAxisLabelPosition == LabelPosition.RIGHT) (xItemSpacing * i) else (xItemSpacing * i) + yAxisPadding.toPx()
            val y = gridHeight - (yItemSpacing * (yAxisData[i].toFloat() / verticalStep))

            offsetList.add(
                Offset(
                    x = x,
                    y = y
                )
            )
        }

        return LineGraphMetrics(
            yAxisPadding = yAxisPadding,
            gridHeight = gridHeight,
            gridWidth = gridWidth,
            verticalStep = verticalStep,
            xItemSpacing = xItemSpacing,
            yItemSpacing = yItemSpacing,
            maxPointsSize = maxPointsSize,
            yAxisData = yAxisData,
            xAxisData = xAxisData,
            yAxisLabels = yAxisLabelList,
            offsetList = offsetList
        )
    }

    /**
     * Drawing Grid lines
     */
    internal fun drawGrid() {
        scope.run {
            if (style.visibility.isGridVisible) {

                for (i in 0 until metrics.maxPointsSize) {

                    // lines inclined towards x axis

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) metrics.xItemSpacing * (i) else (metrics.xItemSpacing * (i)) + metrics.yAxisPadding.toPx()

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(labelXOffset, 0f),
                        end = Offset(labelXOffset, metrics.gridHeight),
                    )
                }

                for (i in 0 until metrics.yAxisLabels.size) {
                    // lines inclined towards y axis

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) 0F else metrics.yAxisPadding.toPx()

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(
                            labelXOffset,
                            metrics.gridHeight - metrics.yItemSpacing * (i)
                        ),
                        end = Offset(
                            metrics.gridWidth,
                            metrics.gridHeight - metrics.yItemSpacing * (i)
                        ),
                    )
                }
            }
        }
    }

    /**
     * Drawing Fill (None / Solid / Gradient)
     */
    internal fun drawFill() {
        scope.run {
            when (val fillType = style.colors.fillType) {

                is LineGraphFillType.None -> { /* NONE */
                }

                else -> {
                    /**
                     * Drawing Solid/Gradient fill for the plotted points
                     * Create Path from the offset list with start and end point to complete the path
                     * then draw path using brush
                     */
                    val path = Path().apply {
                        // starting point for fill (solid/gradient)
                        moveTo(
                            x = if (style.yAxisLabelPosition == LabelPosition.RIGHT) 0F else metrics.yAxisPadding.toPx(),
                            y = metrics.gridHeight
                        )

                        // mid points
                        for (i in 0 until metrics.maxPointsSize) {
                            lineTo(metrics.offsetList[i].x, metrics.offsetList[i].y)
                        }

                        // ending point for fill (solid/gradient)
                        lineTo(
                            x = if (style.yAxisLabelPosition == LabelPosition.RIGHT) metrics.xItemSpacing * (metrics.maxPointsSize - 1) else metrics.gridWidth,
                            y = metrics.gridHeight
                        )

                    }

                    if (fillType is LineGraphFillType.Solid) {
                        // Solid
                        drawPath(
                            path = path,
                            brush = Brush.verticalGradient(listOf(fillType.color, fillType.color,))
                        )
                    } else if (fillType is LineGraphFillType.Gradient) {
                        // Gradient
                        drawPath(
                            path = path,
                            brush = fillType.brush
                        )
                    }
                }
            }
        }
    }

    /**
     * Plotting points on the Graph
     */
    internal fun plotPoints() {
        scope.run {
            metrics.offsetList.forEach { offSet ->
                drawCircle(
                    color = style.colors.pointColor,
                    radius = 5.dp.toPx(),
                    center = Offset(offSet.x, offSet.y)
                )
            }
        }
    }

    /**
     * Drawing line connecting all circles/points
     */
    internal fun drawLine() {
        scope.run {
            drawPoints(
                points = metrics.offsetList.subList(
                    fromIndex = 0,
                    toIndex = metrics.maxPointsSize
                ),
                color = style.colors.lineColor,
                pointMode = PointMode.Polygon,
                strokeWidth = 2.dp.toPx(),
            )
        }
    }

    /**
     * Drawing text labels over the X and Y axis
     */
    internal fun drawTextLabelsOverXAndYAxis() {
        scope.run {
            // Drawing text labels over the x- axis
            if (style.visibility.isXAxisLabelVisible) {

                for (i in 0 until metrics.maxPointsSize) {

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) metrics.xItemSpacing * (i) else (metrics.xItemSpacing * (i)) + metrics.yAxisPadding.toPx()

                    drawContext.canvas.nativeCanvas.drawText(
                        metrics.xAxisData[i],
                        labelXOffset, // x
                        size.height, // y
                        Paint().apply {
                            color = this@LineGraphHelper.style.colors.xAxisTextColor.toArgb()
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }

            //Drawing text labels over the y- axis
            if (style.visibility.isYAxisLabelVisible) {

                val labelXOffset =
                    if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width else 0F

                for (i in 0 until metrics.yAxisLabels.size) {
                    drawContext.canvas.nativeCanvas.drawText(
                        metrics.yAxisLabels[i],
                        labelXOffset, //x
                        metrics.gridHeight - metrics.yItemSpacing * (i + 0), //y
                        Paint().apply {
                            color = this@LineGraphHelper.style.colors.yAxisTextColor.toArgb()
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }
        }
    }
}
