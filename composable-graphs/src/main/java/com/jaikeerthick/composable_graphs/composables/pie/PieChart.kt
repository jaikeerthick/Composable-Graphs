package com.jaikeerthick.composable_graphs.composables.pie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.composables.donut.model.DonutData
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.model.PieSlice
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_HEIGHT


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<PieData>,
    style: PieChartStyle = PieChartStyle(),
    onSliceClick: (PieData)-> Unit
){
    PieChartImpl(
        modifier = modifier,
        data = data,
        style = style,
        onSliceClick = onSliceClick
    )
}

@Composable
private fun PieChartImpl(
    modifier: Modifier,
    data: List<PieData>,
    style: PieChartStyle,
    onSliceClick: (PieData)-> Unit
){

    val defaultModifier = Modifier
        .size(size = DEFAULT_GRAPH_HEIGHT)
        .padding(12.dp)

    val pieSliceList = remember { data.mapToPieSliceList() }

    Canvas(
        modifier = modifier.then(defaultModifier),
        onDraw = {

            val minRadius = listOf(this.size.width, this.size.height).minOf { it }

            pieSliceList.reversed().forEach { value ->
                drawArc(
                    color = value.color,
                    startAngle = - 90F, // -90F to rotate the arcs straight
                    sweepAngle = value.endAngle ,  // minus startAngle from endAngle to make sure we only draw the required arc and not from 0 for every arcs
                    useCenter = true,
                    style = Fill,
                    size = Size(minRadius, minRadius),
                    blendMode = style.blendMode ?: DrawScope.DefaultBlendMode,
                    colorFilter = style.colorFilter
                )
            }
        }
    )
}


@Preview
@Composable
private fun PieChartPreview(){

    val previewData = remember {
        listOf(10, 20, 40, 10).map {
            PieData(value = it.toFloat())
        }
    }

    PieChart(
        data = previewData,
        onSliceClick = {}
    )
}