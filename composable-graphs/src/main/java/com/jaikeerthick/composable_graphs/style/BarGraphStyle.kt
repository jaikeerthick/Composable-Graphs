package com.jaikeerthick.composable_graphs.style

import com.jaikeerthick.composable_graphs.color.BarGraphColors


data class BarGraphStyle(
    val colors: BarGraphColors = BarGraphColors(),
    val visibility: BarGraphVisibility = BarGraphVisibility(),
    //val yAxisLabelPosition: LabelPosition = LabelPosition.LEFT
)


data class BarGraphVisibility(
    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,
)
