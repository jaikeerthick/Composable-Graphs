package com.jaikeerthick.composable_graphs.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.LinearGraphColors


data class LineGraphStyle(

    val paddingValues: PaddingValues = PaddingValues(
        all = 12.dp
    ),
    val height: Dp = 300.dp,
    val colors: LinearGraphColors = LinearGraphColors(),
    val visibility: LinearGraphVisibility = LinearGraphVisibility(),
    val yAxisLabelPosition: LabelPosition = LabelPosition.RIGHT
)


data class LinearGraphVisibility(
    val isCrossHairVisible: Boolean = false,
    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,

    val isHeaderVisible: Boolean = false,
)
