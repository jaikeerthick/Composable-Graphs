package com.jaikeerthick.composable_graphs.composables.pie

import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.model.PieSlice
import com.jaikeerthick.composable_graphs.util.GraphHelper.convertPercentageToDegree


internal class PieChartHelper{

}

internal fun List<PieData>.mapToPieSliceList(): List<PieSlice>{

    val total = this.map{ it.value }.sum()

    var startAngleTemp = 0F

    return this.map { pieData ->


        val percentage = (pieData.value / total) * 100F

        val startAngle = startAngleTemp
        val endAngle = startAngleTemp + convertPercentageToDegree(percentage)

        startAngleTemp = endAngle

        println("JAIKKK --- Percentage: $percentage")
        println("JAIKKK --- Angle: start: $startAngle, end: $endAngle")
        // return
        PieSlice(
            startAngle = startAngle,
            endAngle = endAngle,
            color = pieData.color
        )
    }
}