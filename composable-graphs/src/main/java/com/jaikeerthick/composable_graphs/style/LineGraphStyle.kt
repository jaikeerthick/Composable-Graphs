package com.jaikeerthick.composable_graphs.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.LinearGraphColors

internal val DEFAULT_LINE_GRAPH_HEIGHT = 300.dp
internal val DEFAULT_LINE_GRAPH_PADDING = PaddingValues(horizontal = 10.dp)

data class LineGraphStyle(
    val colors: LinearGraphColors = LinearGraphColors(),
    val visibility: LinearGraphVisibility = LinearGraphVisibility(),
    val yAxisLabelPosition: LabelPosition = LabelPosition.RIGHT
)


data class LinearGraphVisibility(
    val isCrossHairVisible: Boolean = false,
    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,
)
