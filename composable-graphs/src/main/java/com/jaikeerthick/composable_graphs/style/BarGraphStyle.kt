package com.jaikeerthick.composable_graphs.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.BarGraphColors




data class BarGraphStyle(

    val paddingValues: PaddingValues = PaddingValues(
        all = 12.dp
    ),
    val height: Dp = 300.dp,
    val colors: BarGraphColors = BarGraphColors(),
    val visibility: BarGraphVisibility = BarGraphVisibility(),
    //val yAxisLabelPosition: LabelPosition = LabelPosition.LEFT
)


data class BarGraphVisibility(

    val isYAxisLabelVisible: Boolean = false,
    val isXAxisLabelVisible: Boolean = true,
    val isGridVisible: Boolean = false,
    val isHeaderVisible: Boolean = true,
)
