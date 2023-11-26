package com.jaikeerthick.composable_graphs.composables.bar.style

import com.jaikeerthick.composable_graphs.style.LabelPosition


data class BarGraphStyle(
    val colors: BarGraphColors = BarGraphColors(),
    val visibility: BarGraphVisibility = BarGraphVisibility(),
    val yAxisLabelPosition: LabelPosition = LabelPosition.RIGHT
)


data class BarGraphVisibility(
    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,
)
