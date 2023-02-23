package com.jaikeerthick.composable_graphs.composables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.helper.GraphHelper
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun LineGraph(
    xAxisData: List<GraphData>,
    yAxisData: List<Number>,
    header: @Composable() () -> Unit = {},
    style: LineGraphStyle = LineGraphStyle(),
    onPointClicked: (pair: Pair<Any,Any>) -> Unit = {},
) {

    val yAxisPadding: Dp = if (style.visibility.isYAxisLabelVisible) 20.dp else 0.dp
    val paddingBottom: Dp = if (style.visibility.isXAxisLabelVisible) 20.dp else 0.dp

    val offsetList = remember{ mutableListOf<Offset>() }
    val isPointClicked = remember { mutableStateOf(false) }
    val clickedPoint: MutableState<Offset?> = remember { mutableStateOf(null) }


    Column(
        modifier = Modifier
            .background(
                color = style.colors.backgroundColor
            )
            .fillMaxWidth()
            .padding(style.paddingValues)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ){

        if (style.visibility.isHeaderVisible){
            header()
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(style.height)
                .padding(horizontal = 10.dp)
                .pointerInput(true) {

                    detectTapGestures { p1: Offset ->

                        val shortest = offsetList.find { p2: Offset ->

                            /** Pythagorean Theorem
                             * Using Pythagorean theorem to calculate distance between two points :
                             * p1 =  p1(x,y) which is the touch point
                             * p2 =  p2(x,y)) which is the point plotted on graph
                             * Formula: c = sqrt(a² + b²), where a = (p1.x - p2.x) & b = (p1.y - p2.y),
                             * c is the distance between p1 & p2
                            Pythagorean Theorem */

                            val distance = sqrt(
                                (p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2)
                            )
                            val pointRadius = 15.dp.toPx()

                            distance <= pointRadius
                        }

                        shortest?.let {

                            clickedPoint.value = it
                            isPointClicked.value = true

                            //
                            val index = offsetList.indexOf(it)
                            onPointClicked(Pair(xAxisData[index].text, yAxisData[index]))
                        }

                    }

                },
        ) {

            /**
             * xItemSpacing, yItemSpacing => space between each item that lies on the x and y axis
             * (size.width - 16.dp.toPx())
             *               ~~~~~~~~~~~~~ => padding saved for the end of the axis
             */

            val gridHeight = (size.height) - paddingBottom.toPx()
            val gridWidth = if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width - yAxisPadding.toPx() else size.width

            // the maximum points for x and y axis to plot (maintain uniformity)
            val maxPointsSize: Int = minOf(xAxisData.size, yAxisData.size)

            // maximum of the y data list
            val absMaxY = GraphHelper.getAbsoluteMax(yAxisData)
            val absMinY = 0

            val verticalStep = absMaxY.toInt() / maxPointsSize.toFloat()

            // generate y axis label
            val yAxisLabelList = mutableListOf<String>()

            for (i in 0..maxPointsSize) {
                val intervalValue = (verticalStep*i).roundToInt()
                println("interval - $intervalValue")
                yAxisLabelList.add(intervalValue.toString())
            }


            val xItemSpacing = if (style.yAxisLabelPosition == LabelPosition.RIGHT){
                gridWidth / (maxPointsSize - 1)
            } else (gridWidth - yAxisPadding.toPx()) / (maxPointsSize - 1)
            val yItemSpacing = gridHeight / (yAxisLabelList.size - 1)



            /**
             * Drawing Grid lines
             */
            if (style.visibility.isGridVisible) {

                for (i in 0 until maxPointsSize) {

                    // lines inclined towards x axis

                    val labelXOffset = if (style.yAxisLabelPosition == LabelPosition.RIGHT) xItemSpacing * (i) else (xItemSpacing * (i)) + yAxisPadding.toPx()

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(labelXOffset, 0f),
                        end = Offset(labelXOffset, gridHeight),
                    )
                }

                for (i in 0 until yAxisLabelList.size){
                    // lines inclined towards y axis

                    val labelXOffset = if (style.yAxisLabelPosition == LabelPosition.RIGHT) 0F else yAxisPadding.toPx()

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(labelXOffset, gridHeight - yItemSpacing * (i)),
                        end = Offset(gridWidth, gridHeight - yItemSpacing * (i)),
                    )
                }
            }

            /**
             * Drawing text labels over the x- axis
             */
            if (style.visibility.isXAxisLabelVisible) {

                for (i in 0 until maxPointsSize) {

                    val labelXOffset = if (style.yAxisLabelPosition == LabelPosition.RIGHT) xItemSpacing * (i) else (xItemSpacing * (i)) + yAxisPadding.toPx()

                    drawContext.canvas.nativeCanvas.drawText(
                        "${xAxisData[i].text}",
                        labelXOffset, // x
                        size.height, // y
                        Paint().apply {
                            color = android.graphics.Color.GRAY
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }

            /**
             * Drawing text labels over the y- axis
             */

            if (style.visibility.isYAxisLabelVisible) {

                val labelXOffset = if (style.yAxisLabelPosition == LabelPosition.RIGHT) size.width else 0F

                for (i in 0 until yAxisLabelList.size) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${yAxisLabelList[i]}",
                        labelXOffset, //x
                        gridHeight - yItemSpacing * (i + 0), //y
                        Paint().apply {
                            color = android.graphics.Color.GRAY
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }


            // plotting points
            /**
             * Plotting points on the Graph
             */

            offsetList.clear() // clearing list to avoid data duplication during recomposition

            for (i in 0 until maxPointsSize) {

                //val pos = if (style.yAxisLabelPosition == LabelPosition.RIGHT) i else (i+1)

                //val x1 = xItemSpacing * pos
                val x1 = if (style.yAxisLabelPosition == LabelPosition.RIGHT) (xItemSpacing * i) else (xItemSpacing * i) + yAxisPadding.toPx()
                val y1 = gridHeight - (yItemSpacing * (yAxisData[i].toFloat() / verticalStep.toFloat()))

                offsetList.add(
                    Offset(
                        x = x1,
                        y = y1
                    )
                )

                drawCircle(
                    color = style.colors.pointColor,
                    radius = 5.dp.toPx(),
                    center = Offset(x1, y1)
                )
            }


            /**
             * Drawing Gradient fill for the plotted points
             * Create Path from the offset list with start and end point to complete the path
             * then draw path using brush
             */
            val path = Path().apply {
                // starting point for gradient
                moveTo(
                    x = if (style.yAxisLabelPosition == LabelPosition.RIGHT) 0F else yAxisPadding.toPx(),
                    y = gridHeight
                )

                for (i in 0 until maxPointsSize) {
                    lineTo(offsetList[i].x, offsetList[i].y)
                }

                // ending point for gradient
                lineTo(
                    x = if (style.yAxisLabelPosition == LabelPosition.RIGHT) xItemSpacing * (yAxisData.size - 1) else gridWidth,
                    y = gridHeight
                )

            }

            drawPath(
                path = path,
                brush = style.colors.fillGradient ?: Brush.verticalGradient(
                    listOf(Color.Transparent, Color.Transparent)
                )
            )


            /**
             * drawing line connecting all circles/points
             */
            drawPoints(
                points = offsetList.subList(
                    fromIndex = 0,
                    toIndex = maxPointsSize
                ),
                color = style.colors.lineColor,
                pointMode = PointMode.Polygon,
                strokeWidth = 2.dp.toPx(),
            )

            /**
             * highlighting clicks when user clicked on the canvas
             */
            clickedPoint.value?.let {
                drawCircle(
                    color = style.colors.clickHighlightColor,
                    center = it,
                    radius = 12.dp.toPx()
                )
                if (style.visibility.isCrossHairVisible) {
                    drawLine(
                        color = style.colors.crossHairColor,
                        start = Offset(it.x, 0f),
                        end = Offset(it.x, gridHeight),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(15f, 15f)
                        )
                    )
                }
            }

        }
    }


}


