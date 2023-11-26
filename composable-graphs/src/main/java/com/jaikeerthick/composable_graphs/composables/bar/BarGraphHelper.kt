package com.jaikeerthick.composable_graphs.composables.bar

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphFillType
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.theme.LightGray
import com.jaikeerthick.composable_graphs.util.GraphHelper
import com.jaikeerthick.composable_graphs.util.logDebug
import kotlin.math.roundToInt

internal data class BarGraphMetrics(
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
    val offsetList: List<Pair<Offset, Offset>>,
)

internal class BarGraphHelper(
    private val scope: DrawScope,
    private val style: BarGraphStyle,
    private val yAxisData: List<Number>,
    private val xAxisData: List<String>,
) {

    internal val metrics = scope.buildMetrics()

    private fun DrawScope.buildMetrics(): BarGraphMetrics {

        val yAxisPadding: Dp = if (style.visibility.isYAxisLabelVisible) 20.dp else 0.dp
        val paddingBottom: Dp = if (style.visibility.isXAxisLabelVisible) 20.dp else 0.dp

        val gridHeight = (size.height) - paddingBottom.toPx()
        val gridWidth =
            if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width - yAxisPadding.toPx() else size.width

        val maxPointsSize = yAxisData.size + 1

        val offsetList = mutableListOf<Pair<Offset, Offset>>()

        // maximum of the data list
        val absMaxY = GraphHelper.getAbsoluteMax(yAxisData)
        val absMinY = 0
        logDebug(message = "max - $absMaxY, Min - $absMinY")

        val verticalStep = absMaxY.toInt() / yAxisData.size.toFloat()

        val yAxisLabelList = mutableListOf<String>()

        logDebug(message = "max point size - $maxPointsSize")


        for (i in 0..yAxisData.size) {
            yAxisLabelList.add((verticalStep * i).roundToInt().toString())
            logDebug(message = "interval - ${(absMinY + verticalStep * i)}")
        }


        val xItemSpacing = if (style.yAxisLabelPosition == LabelPosition.RIGHT) {
            gridWidth / (maxPointsSize - 1)
        } else (gridWidth - yAxisPadding.toPx()) / (maxPointsSize - 1)
        val yItemSpacing = gridHeight / (yAxisLabelList.size - 1)

        offsetList.clear()  // clearing list to avoid data duplication during recomposition
        for (i in 0 until maxPointsSize - 1) {
            val x1 = if (style.yAxisLabelPosition == LabelPosition.RIGHT) (xItemSpacing * i) else (xItemSpacing * i) + yAxisPadding.toPx()
            val x2 = if (style.yAxisLabelPosition == LabelPosition.RIGHT) (xItemSpacing * (i + 1)) else (xItemSpacing * (i + 1)) + yAxisPadding.toPx()
            val y1 = gridHeight - (yItemSpacing * (yAxisData[i].toFloat() / verticalStep))
            val y2 = y1

            offsetList.add(
                Pair(
                    first = Offset(
                        x = x1,
                        y = y1
                    ),
                    second = Offset(
                        x = x2,
                        y = y2
                    ),
                )
            )
        }

        return BarGraphMetrics(
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
     * Drawing Grid lines behind the graph on x and y axis
     */
    internal fun drawGrid() {
        scope.run {
            if (style.visibility.isGridVisible) {
                // lines inclined towards x axis
                for (i in 0 until metrics.maxPointsSize) {

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) metrics.xItemSpacing * (i) else (metrics.xItemSpacing * (i)) + metrics.yAxisPadding.toPx()

                    drawLine(
                        color = LightGray,
                        start = Offset(labelXOffset, 0f),
                        end = Offset(labelXOffset, metrics.gridHeight),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // lines inclined towards y axis
                for (i in 0 until metrics.yAxisLabels.size) {

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) 0F else metrics.yAxisPadding.toPx()

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(
                            labelXOffset,
                            metrics.gridHeight - metrics.yItemSpacing * (i + 0)
                        ),
                        end = Offset(
                            metrics.gridWidth,
                            (metrics.gridHeight) - metrics.yItemSpacing * (i + 0)
                        ),
                    )
                }
            }
        }
    }

    /**
     * Drawing text labels over the X and Y axis
     */
    internal fun drawTextLabelsOverXAndYAxis() {
        scope.run {
            // Drawing text labels over the x- axis
            if (style.visibility.isXAxisLabelVisible) {
                // Here we use a different looping strategy: for(i in 0 until maxPointsSize-1)
                // until maxPointsSize-1 -> because we need (n-1) count for n number of values
                // i.e, We need to center align the x value texts.
                for (i in 0 until metrics.maxPointsSize - 1) {

                    val xItemPosition =
                        ((metrics.xItemSpacing * i) + (metrics.xItemSpacing * (i + 1))) / 2 // center of two points n & n+1

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) xItemPosition else xItemPosition + metrics.yAxisPadding.toPx()

                    drawContext.canvas.nativeCanvas.drawText(
                        xAxisData[i],
                        labelXOffset, // x
                        size.height, // y
                        Paint().apply {
                            color = this@BarGraphHelper.style.colors.xAxisTextColor.toArgb()
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }

            //Drawing text labels over the y- axis
            if (style.visibility.isYAxisLabelVisible) {
                for (i in 0 until metrics.yAxisLabels.size) {

                    val labelXOffset =
                        if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width else 0F

                    drawContext.canvas.nativeCanvas.drawText(
                        metrics.yAxisLabels[i],
                        labelXOffset, //x
                        metrics.gridHeight - metrics.yItemSpacing * (i + 0), //y
                        Paint().apply {
                            color = this@BarGraphHelper.style.colors.yAxisTextColor.toArgb()
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }
        }
    }

    /**
     * Draw Rectangle bars using calculated [offsetList] values
     */
    internal fun drawBars() {

        val brush: Brush = when (style.colors.fillType) {
            is BarGraphFillType.Solid -> {
                Brush.linearGradient(colors = listOf(style.colors.fillType.color, style.colors.fillType.color,))
            }
            is BarGraphFillType.Gradient -> {
                style.colors.fillType.brush
            }
        }

        scope.run {
            metrics.offsetList.forEach { offsetPair ->

                val offset1 = offsetPair.first
                //val offset2 = offsetPair.second

                drawRect(
                    brush = brush,
                    topLeft = Offset(
                        x = offset1.x,
                        y = offset1.y
                    ),
                    size = Size(
                        width = metrics.xItemSpacing,
                        height = metrics.gridHeight - offset1.y
                    )
                )
            }
        }
    }
}