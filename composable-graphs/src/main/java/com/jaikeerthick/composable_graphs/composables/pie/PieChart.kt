package com.jaikeerthick.composable_graphs.composables.pie

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.model.PieSlice
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import com.jaikeerthick.composable_graphs.theme.GraphAccent2
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_HEIGHT
import com.jaikeerthick.composable_graphs.util.GraphHelper
import com.jaikeerthick.composable_graphs.util.GraphHelper.centerOf
import com.jaikeerthick.composable_graphs.util.GraphHelper.getAngleFromOffset
import com.jaikeerthick.composable_graphs.util.GraphHelper.getOffsetOfAngle
import kotlinx.coroutines.delay
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<PieData>,
    style: PieChartStyle = PieChartStyle(),
    onSliceClick: (PieData) -> Unit
) {
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
    onSliceClick: (PieData) -> Unit
) {

    val pieSliceList = remember { mutableListOf<PieSlice>() }
    var radiusF = remember { 0F }
    var centerF = remember { Offset(0F, 0F) }
    var clickedSlice: PieSlice? by remember { mutableStateOf(null) }


    val defaultModifier = Modifier
        .size(size = DEFAULT_GRAPH_HEIGHT)
        .padding(12.dp)
        .pointerInput(true) {
            detectTapGestures { p1: Offset ->

                val isInsideCircle: Boolean = let {
                    val clickRadius = sqrt((p1.x - centerF.x).pow(2) + (p1.y - centerF.y).pow(2))
                    clickRadius <= radiusF
                }

                if (isInsideCircle) {

                    var touchAngle = getAngleFromOffset(
                        offset = p1,
                        radiusF
                    ) - 270F // -270 -> because we are shifting the chart by -90F in all places

                    while (touchAngle < 0F) {
                        touchAngle += 360.0F
                    }

                    val slice = pieSliceList.find { slice ->

                        //println("JAIKKK--- check --- : start: ${slice.startAngle}, end: ${slice.endAngle}, clicked: $touchAngle")
                        (touchAngle >= slice.startAngle && touchAngle <= slice.endAngle)
                    }

                    clickedSlice = slice
                    //println("JAIKKK, clicked slice = ${slice?.label}")
                }
            }
        }

    LaunchedEffect(key1 = clickedSlice, block = {
        if (clickedSlice != null){
            println("JAIKKK, click detected.. attempting to reduce alpha")
           pieSliceList.forEach { slice ->
               slice.color.value = slice.color.value.copy(alpha = 0.2F)
           }
        }
    })


    Canvas(
        modifier = modifier.then(defaultModifier),
        onDraw = {

            val minWidthAndHeight = listOf(this.size.width, this.size.height).minOf { it }
            val radius = minWidthAndHeight / 2
            radiusF = radius
            centerF = center

            pieSliceList.apply {
                clear()
                addAll(data.mapToPieSliceList(this@Canvas, radius))
            }

            /**
             * Drawing slices of pie
             */
            pieSliceList.reversed().forEach { value ->
                drawArc(
                    color = value.color.value,
                    startAngle = -90F, // -90F to rotate the arcs straight
                    sweepAngle = value.endAngle,  // minus startAngle from endAngle to make sure we only draw the required arc and not from 0 for every arcs
                    useCenter = true,
                    style = Fill,
                    size = Size(minWidthAndHeight, minWidthAndHeight),
                    blendMode = style.colors.blendMode ?: DrawScope.DefaultBlendMode,
                    colorFilter = style.colors.colorFilter
                )
            }

            val marginBetweenTexts =
                if (style.visibility.isLabelVisible && style.visibility.isPercentageVisible) {
                    7.sp.toPx()
                } else 0.sp.toPx()

            /**
             * Drawing label inside slices
             */
            if (style.visibility.isLabelVisible) {

                pieSliceList.reversed().forEach { value ->

                    if (value.label != null) {

                        val midAngle = ((value.startAngle + value.endAngle) / 2) - 90F
                        val midOffSet = getOffsetOfAngle(angle = midAngle, radius = radius)

                        val centerOfSlice = centerOf(midOffSet, center)

                        drawContext.canvas.nativeCanvas.drawText(
                            value.label,
                            centerOfSlice.x, // x
                            centerOfSlice.y - marginBetweenTexts, // y
                            Paint().apply {
                                color = value.labelColor?.toArgb() ?: Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                textSize = 12.sp.toPx()
                            }
                        )
                    }
                }
            }

            /**
             * Drawing percentage inside slices
             */
            if (style.visibility.isPercentageVisible) {

                pieSliceList.reversed().forEach { value ->

                    val midAngle = ((value.startAngle + value.endAngle) / 2) - 90F
                    val midOffSet = getOffsetOfAngle(angle = midAngle, radius = radius)

                    val centerOfSlice = centerOf(midOffSet, center)

                    drawContext.canvas.nativeCanvas.drawText(
                        value.percentage,
                        centerOfSlice.x, // x
                        centerOfSlice.y + marginBetweenTexts, // y
                        Paint().apply {
                            color = value.labelColor?.toArgb() ?: Color.White.toArgb()
                            textAlign = Paint.Align.CENTER
                            textSize = 12.sp.toPx()
                        }
                    )
                }
            }
        }
    )
}


@Preview
@Composable
private fun PieChartPreview() {

    val previewData = remember {
        listOf(
            PieData(value = 130F, label = "HTC"),
            PieData(value = 260F, label = "Apple"),
            PieData(value = 500F, label = "Google"),
        )
    }

    PieChart(
        data = previewData,
        style = PieChartStyle(
            visibility = PieChartVisibility(
                isLabelVisible = true,
                isPercentageVisible = true
            )
        ),
        onSliceClick = {}
    )
}