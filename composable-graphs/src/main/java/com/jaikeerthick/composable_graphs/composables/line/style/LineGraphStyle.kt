package com.jaikeerthick.composable_graphs.composables.line.style

import com.jaikeerthick.composable_graphs.style.LabelPosition


data class LineGraphStyle(
    val colors: LineGraphColors = LineGraphColors(),
    val visibility: LineGraphVisibility = LineGraphVisibility(),
    val yAxisLabelPosition: LabelPosition = LabelPosition.RIGHT
)


data class LineGraphVisibility(
    val isCrossHairVisible: Boolean = false,
    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,
)
