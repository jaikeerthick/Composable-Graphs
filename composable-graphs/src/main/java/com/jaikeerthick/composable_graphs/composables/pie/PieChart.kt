package com.jaikeerthick.composable_graphs.composables.pie

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
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
import com.jaikeerthick.composable_graphs.util.DEFAULT_GRAPH_SIZE
import com.jaikeerthick.composable_graphs.util.GraphHelper.centerOf
import com.jaikeerthick.composable_graphs.util.GraphHelper.getAngleFromOffset
import com.jaikeerthick.composable_graphs.util.GraphHelper.getOffsetOfAngle
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<PieData>,
    style: PieChartStyle = PieChartStyle(),
    onSliceClick: ((PieData) -> Unit)? = null
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
    onSliceClick: ((PieData) -> Unit)? = null
) {

    val pieSliceList = remember { mutableListOf<PieSlice>() }
    var radiusF = remember { 0F }
    var centerF = remember { Offset(0F, 0F) }

    val tapDetectModifier = if (onSliceClick == null) Modifier else {
        Modifier
            .pointerInput(Unit) {
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

                        val clickedSlice = pieSliceList.find { slice ->
                            (touchAngle >= slice.startAngle && touchAngle <= slice.endAngle)
                        }
                        val clickedPieData = data.find { clickedSlice?.label == it.label }
                        clickedPieData?.let { onSliceClick(it) }
                    }
                }
            }
    }

    val defaultModifier = Modifier
        .size(size = DEFAULT_GRAPH_SIZE)
        .then(tapDetectModifier)


    Canvas(
        modifier = modifier.then(defaultModifier),
        onDraw = {

            val minWidthAndHeight = listOf(this.size.width, this.size.height).minOf { it }

            /**
             * Don't use [center] or [size] of canvas
             * Only use [pieCenter] and [pieSize] because we are maintaining same width and height
             * for the Pie
             */
            val pieSize = Size(minWidthAndHeight, minWidthAndHeight)
            val pieCenter = Offset(x = pieSize.width/2, y = pieSize.height/2)
            val radius = minWidthAndHeight / 2

            radiusF = radius
            centerF = pieCenter

            pieSliceList.apply {
                clear()
                addAll(data.mapToPieSliceList(radius, pieSize))
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
                    size = pieSize,
                    blendMode = style.colors.blendMode,
                    colorFilter = style.colors.colorFilter
                )
            }

            val defaultTextSize = if ((radiusF/16).sp >= 14.sp) 14.sp else (radiusF/16).sp
            val marginBetweenTexts =
                if (style.visibility.isLabelVisible && style.visibility.isPercentageVisible) {
                    (defaultTextSize/1.5).toPx()
                } else 0.sp.toPx()

            /**
             * Drawing label inside slices
             */
            if (style.visibility.isLabelVisible) {

                pieSliceList.reversed().forEach { value ->

                    if (value.label != null) {

                        val midAngle = ((value.startAngle + value.endAngle) / 2) - 90F
                        val midOffSet = getOffsetOfAngle(angle = midAngle, radius = radius, pieSize = pieSize)

                        val centerOfSlice = centerOf(midOffSet, pieCenter)

                        drawContext.canvas.nativeCanvas.drawText(
                            value.label,
                            centerOfSlice.x, // x
                            centerOfSlice.y - marginBetweenTexts, // y
                            Paint().apply {
                                color = value.labelColor?.toArgb() ?: Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                this.textSize = defaultTextSize.toPx()
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
                    val midOffSet = getOffsetOfAngle(angle = midAngle, radius = radius, pieSize = pieSize)

                    val centerOfSlice = centerOf(midOffSet, pieCenter)

                    drawContext.canvas.nativeCanvas.drawText(
                        value.percentage,
                        centerOfSlice.x, // x
                        centerOfSlice.y + marginBetweenTexts, // y
                        Paint().apply {
                            color = value.labelColor?.toArgb() ?: Color.White.toArgb()
                            textAlign = Paint.Align.CENTER
                            this.textSize = defaultTextSize.toPx()
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
        modifier = Modifier
            .padding(12.dp),
        data = previewData,
        style = PieChartStyle(
            visibility = PieChartVisibility(
                isLabelVisible = true,
                isPercentageVisible = true
            )
        )
    )
}