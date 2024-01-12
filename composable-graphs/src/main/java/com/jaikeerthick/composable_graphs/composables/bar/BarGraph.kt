package com.jaikeerthick.composable_graphs.composables.bar

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_SIZE
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_PADDING


@Composable
fun BarGraph(
    modifier: Modifier = Modifier,
    data: List<BarData>,
    style: BarGraphStyle = BarGraphStyle(),
    onBarClick: (data: BarData) -> Unit = {},
) {
    BarGraphImpl(
        modifier = modifier,
        xAxisData = data.map { it.x },
        yAxisData = data.map { it.y },
        style = style,
        onBarClick = onBarClick
    )
}

@Composable
private fun BarGraphImpl(
    modifier: Modifier,
    xAxisData: List<String>,
    yAxisData: List<Number>,
    style: BarGraphStyle,
    onBarClick: (value: BarData) -> Unit,
) {

    val barOffsetList = remember { mutableListOf<Pair<Offset, Offset>>() }

    val clickedBar: MutableState<Offset?> = remember { mutableStateOf(null) }

    val defaultModifier = Modifier
        .fillMaxWidth()
        .height(height = DEFAULT_GRAPH_SIZE)
        .padding(paddingValues = DEFAULT_GRAPH_PADDING)
        .pointerInput(true) {
            detectTapGestures { p1: Offset ->
                val click = barOffsetList.find {
                    (it.first.x < p1.x) && (it.second.x > p1.x) // check if our touch point is between left and right side of the bar
                            && (p1.y > it.first.y) // check if our touch point is below the top of the bar
                }
                click?.let {
                    val index = barOffsetList.indexOf(it)
                    onBarClick(
                        BarData(x = xAxisData[index], y = yAxisData[index])
                    )
                    clickedBar.value = it.first
                }
            }
        }

    Canvas(modifier = modifier.then(defaultModifier)) {

        val barGraphHelper = BarGraphHelper(
            scope = this,
            style = style,
            yAxisData = yAxisData,
            xAxisData = xAxisData
        )


        barOffsetList.clear()
        barOffsetList.addAll(barGraphHelper.metrics.offsetList)

        /**
         * Drawing Grid lines behind the graph on x and y axis
         */
        barGraphHelper.drawGrid()

        /**
         * Drawing text labels over the X and Y axis
         */
        barGraphHelper.drawTextLabelsOverXAndYAxis()

        /**
         * Draw Rectangle using calculated values
         */
        barGraphHelper.drawBars()

        /**
         * highlighting clicks when user clicked on the specific area in canvas,
         */
        clickedBar.value?.let {
            drawRect(
                color = style.colors.clickHighlightColor,
                topLeft = Offset(
                    x = it.x,
                    y = it.y
                ),
                size = Size(
                    width = barGraphHelper.metrics.xItemSpacing,
                    height = barGraphHelper.metrics.gridHeight - it.y
                )
            )
        }
    }
}

