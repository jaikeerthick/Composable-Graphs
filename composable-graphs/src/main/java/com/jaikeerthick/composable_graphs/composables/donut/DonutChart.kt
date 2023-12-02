package com.jaikeerthick.composable_graphs.composables.donut

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.composables.donut.model.DonutData
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartStyle
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartType
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_HEIGHT


/**
 * Donut Chart
 */
@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    type: DonutChartType = DonutChartType.Normal,
    data: List<DonutData>,
    style: DonutChartStyle = DonutChartStyle(),
    onSliceClick: (DonutData)-> Unit
) {
    DonutChartImpl(
        modifier = modifier,
        type = type,
        data = data,
        style = style,
        onPointClick = onSliceClick
    )
}

@Composable
private fun DonutChartImpl(
    modifier: Modifier,
    type: DonutChartType,
    data: List<DonutData>,
    style: DonutChartStyle,
    onPointClick: (DonutData) -> Unit,
) {

    val defaultModifier = Modifier
        .size(size = DEFAULT_GRAPH_HEIGHT)
        .padding(12.dp)

    val donutSliceList = remember { data.mapToDonutSliceList() }

    val ringSize = remember { 50.dp.value }

    Canvas(
        modifier = modifier.then(defaultModifier),
        onDraw = {

            donutSliceList.reversed().forEach { value ->
                drawArc(
                    color = value.color,
                    startAngle = - 90F, // -90F to rotate the arcs straight
                    sweepAngle = value.endAngle ,  // minus startAngle from endAngle to make sure we only draw the required arc and not from 0 for every arcs
                    useCenter = false,
                    style = Stroke(
                        width = ringSize,
                        cap = StrokeCap.Round,
                    ),
                    size = this.size.copy(
                        width = this.size.width - ringSize,
                        height = this.size.width - ringSize,
                    ),
                    topLeft = Offset(ringSize/2F, ringSize/2F),
                    blendMode = style.blendMode ?: DrawScope.DefaultBlendMode,
                    colorFilter = style.colorFilter
                )
            }
        }
    )
}

@Preview
@Composable
private fun DonutChartPreview() {

    val previewData = remember {
        listOf(10, 20, 40, 10).map {
            DonutData(value = it.toFloat())
        }
    }

    DonutChart(
        data = previewData,
        onSliceClick = {}
    )
}

