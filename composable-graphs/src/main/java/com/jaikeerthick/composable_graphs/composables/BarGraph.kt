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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.color.Gradient1
import com.jaikeerthick.composable_graphs.color.Gradient2
import com.jaikeerthick.composable_graphs.color.LightGray
import com.jaikeerthick.composable_graphs.helper.GraphHelper
import com.jaikeerthick.composable_graphs.style.BarGraphStyle
import kotlin.math.roundToInt

/**
 * A minimal and stunning Graph
 */
@Composable
fun BarGraph(
    dataList: List<Number>,
    header: @Composable() () -> Unit = {},
    style: BarGraphStyle = BarGraphStyle(),
    onBarClicked: (value: Any) -> Unit = {},
) {

    val paddingRight: Dp = if (style.visibility.isYAxisLabelVisible) 20.dp else 0.dp
    val paddingBottom: Dp = if (style.visibility.isXAxisLabelVisible) 20.dp else 0.dp

    val offsetList = remember { mutableListOf<Offset>() }

    val barOffsetList = remember { mutableListOf<Pair<Offset, Offset>>() }

    //val isBarClicked = remember { mutableStateOf(false) }
    val clickedBar: MutableState<Offset?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .background(
                color = style.colors.backgroundColor
            )
            .fillMaxWidth()
            .padding(style.paddingValues)
            .wrapContentHeight()
    ) {

        if (style.visibility.isHeaderVisible){
            header()
        }


        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(style.height)
                .padding(horizontal = 1.dp)
                .padding(top = 12.dp) // to prevent overlap with header
                .pointerInput(true) {

                    detectTapGestures { p1: Offset ->

                        val click = barOffsetList.find {

                            (it.first.x < p1.x) && (it.second.x > p1.x) // check if our touch point is between left and right side of the bar
                                    && (p1.y > it.first.y) // check if our touch point is below the top of the bar
                        }

                        click?.let {

                            val index = barOffsetList.indexOf(it)
                            onBarClicked(dataList[index])

                            clickedBar.value = it.first
                        }


                    }

                },
        ) {


            val gridHeight = (size.height) - paddingBottom.toPx()
            val gridWidth = size.width - paddingRight.toPx()

            val maxPointsSize = dataList.size + 1

            // maximum of the data list
            val absMaxY = GraphHelper.getAbsoluteMax(dataList)
            val absMinY = 0
            println("max - $absMaxY, Min - $absMinY")

            val verticalStep = absMaxY.toInt() / dataList.size.toFloat()

            val yAxisLabelList = mutableListOf<String>()

            println("max point size - $maxPointsSize")


            for (i in 0..dataList.size) {
                yAxisLabelList.add((verticalStep * i).roundToInt().toString())
                println("interval - ${(absMinY + verticalStep * i)}")
            }


            val xItemSpacing = gridWidth / (maxPointsSize - 1)
            val yItemSpacing = gridHeight / (yAxisLabelList.size - 1)


            /**
             * Drawing Grid lines behind the graph on x and y axis
             */
            if (style.visibility.isGridVisible) {
                // lines inclined towards x axis
                for (i in 0 until maxPointsSize) {

                    drawLine(
                        color = LightGray,
                        start = Offset(xItemSpacing * (i), 0f),
                        end = Offset(xItemSpacing * (i), gridHeight),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // lines inclined towards y axis
                for (i in 0 until yAxisLabelList.size) {

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, gridHeight - yItemSpacing * (i + 0)),
                        end = Offset(gridWidth, (gridHeight) - yItemSpacing * (i + 0)),
                    )
                }
            }


            /**
             * Drawing text labels over the y- axis
             */
            if (style.visibility.isYAxisLabelVisible) {
                for (i in 0 until yAxisLabelList.size) {

                    drawContext.canvas.nativeCanvas.drawText(
                        "${yAxisLabelList[i]}",
                        size.width, //x
                        gridHeight - yItemSpacing * (i + 0), //y
                        Paint().apply {
                            color = style.colors.yAxisTextColor
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }

            /**
             * Drawing text labels over the x- axis
             */
            if (style.visibility.isXAxisLabelVisible) {
                for (i in 0 until maxPointsSize) {

                    drawContext.canvas.nativeCanvas.drawText(
                        "$i",
                        xItemSpacing * (i), // x
                        size.height, // y
                        Paint().apply {
                            color = style.colors.xAxisTextColor
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }


            /**
             * Calculating Offsets, add those into offsetList
             * Draw Rectangle using calculated values
             */
            offsetList.clear()

            //println("max points size - $maxPointsSize")
            for (i in 0 until maxPointsSize - 1) {

                val x1 = xItemSpacing * i
                //val x2 = xItemSpacing * (i + 1)
                val y1 =
                    gridHeight - (yItemSpacing * (dataList[i].toFloat() / verticalStep))


                barOffsetList.add(
                    Pair(
                        first = Offset(
                            x = x1,
                            y = y1
                        ),
                        second = Offset(
                            x = (xItemSpacing * (i + 1)),
                            y = y1
                        ),
                    )
                )

                drawRect(
                    brush = style.colors.fillGradient ?: Brush.verticalGradient(
                        listOf(Gradient1, Gradient2)
                    ),
                    topLeft = Offset(
                        x = x1,
                        y = y1
                    ),
                    size = Size(
                        width = xItemSpacing,
                        height = gridHeight - y1
                    )
                )

            }

            // click action
            clickedBar.value?.let{

                drawRect(
                    color = style.colors.clickHighlightColor,
                    topLeft = Offset(
                        x = it.x,
                        y = it.y
                    ),
                    size = Size(
                        width = xItemSpacing,
                        height = gridHeight - it.y
                    )
                )

            }

        }

    }


}
