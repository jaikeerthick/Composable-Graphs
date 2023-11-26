package com.jaikeerthick.composable_graphs.composables.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_HEIGHT
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_PADDING
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun LineGraph(
    modifier: Modifier = Modifier,
    data: List<LineData>,
    style: LineGraphStyle = LineGraphStyle(),
    onPointClick: (data: LineData) -> Unit = {},
){
    LineGraphImpl(
        modifier = modifier,
        xAxisData = data.map { it.x },
        yAxisData = data.map { it.y },
        style = style,
        onPointClick = onPointClick
    )
}

@Composable
private fun LineGraphImpl(
    modifier: Modifier,
    xAxisData: List<String>,
    yAxisData: List<Number>,
    style: LineGraphStyle,
    onPointClick: (data: LineData) -> Unit,
) {


    val isPointClicked = remember { mutableStateOf(false) }
    val clickedPoint: MutableState<Offset?> = remember { mutableStateOf(null) }

    val offsetList = remember{ mutableListOf<Offset>() }

    val defaultModifier = Modifier
        .fillMaxWidth()
        .height(height = DEFAULT_GRAPH_HEIGHT)
        .padding(paddingValues = DEFAULT_GRAPH_PADDING)
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
                    onPointClick(
                        LineData(
                            x = xAxisData[index],
                            y = yAxisData[index]
                        )
                    )
                }
            }
        }


        Canvas(
            modifier = modifier
                .then(defaultModifier)
        ) {

            val lineGraphHelper = LineGraphHelper(
                scope = this,
                style = style,
                yAxisData = yAxisData,
                xAxisData = xAxisData
            )


            offsetList.clear()
            offsetList.addAll(lineGraphHelper.metrics.offsetList)

            /**
             * Drawing Grid lines
             */
            lineGraphHelper.drawGrid()

            /**
             * Drawing text labels over the X and Y axis
             */
            lineGraphHelper.drawTextLabelsOverXAndYAxis()


            /**
             * Drawing Fill (None / Solid / Gradient)
             */
            lineGraphHelper.drawFill()


            /**
             * Plotting points on the Graph
             */
            lineGraphHelper.plotPoints()

            /**
             * Drawing line connecting all circles/points
             */
            lineGraphHelper.drawLine()


            /**
             * highlighting clicks when user clicked on the specific area in canvas,
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
                        end = Offset(it.x, lineGraphHelper.metrics.gridHeight),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(15f, 15f)
                        )
                    )
                }
            }

        }
}


