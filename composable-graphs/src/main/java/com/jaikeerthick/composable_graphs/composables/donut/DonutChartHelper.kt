package com.jaikeerthick.composable_graphs.composables.donut

import com.jaikeerthick.composable_graphs.composables.donut.model.DonutSlice
import com.jaikeerthick.composable_graphs.composables.donut.model.DonutData
import com.jaikeerthick.composable_graphs.util.GraphHelper.convertPercentageToDegree


internal class DonutChartHelper {

}

internal fun List<DonutData>.mapToDonutSliceList(): List<DonutSlice>{

    val total = this.map{ it.value }.sum()

    var startAngleTemp = 0F

    return this.map { donutData ->


        val percentage = (donutData.value / total) * 100F

        val startAngle = startAngleTemp
        val endAngle = startAngleTemp + convertPercentageToDegree(percentage)

        startAngleTemp = endAngle

        println("JAIKKK --- Percentage: $percentage")
        println("JAIKKK --- Angle: start: $startAngle, end: $endAngle")
        // return
        DonutSlice(
            startAngle = startAngle,
            endAngle = endAngle,
            color = donutData.color
        )
    }
}