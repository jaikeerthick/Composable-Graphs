package com.jaikeerthick.composable_graphs.composables.pie

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.model.PieSlice
import com.jaikeerthick.composable_graphs.util.GraphHelper.convertPercentageToDegree
import com.jaikeerthick.composable_graphs.util.GraphHelper.getOffsetOfAngle
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt


internal class PieChartHelper{

}

internal fun List<PieData>.mapToPieSliceList(scope: DrawScope, radius: Float): List<PieSlice>{

    val total = this.map{ it.value }.sum()

    var startAngleTemp = 0F

    return this.map { pieData ->


        val percentage = (pieData.value / total) * 100F

        val startAngle = startAngleTemp
        val endAngle = startAngleTemp + convertPercentageToDegree(percentage)

        val startOffset = scope.getOffsetOfAngle(angle = startAngle, radius = radius)
        val endOffset = scope.getOffsetOfAngle(angle = endAngle, radius = radius)

        startAngleTemp = endAngle

        val reducedPercentage = BigDecimal(percentage.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        val consumablePercentage = reducedPercentage.toFloat().toString() + "%"

        // return
        PieSlice(
            startAngle = startAngle,
            startOffset = startOffset,
            endAngle = endAngle,
            endOffset = endOffset,
            percentage = consumablePercentage,
            color = mutableStateOf(pieData.color),
            label = pieData.label,
            labelColor = pieData.labelColor
        )
    }
}