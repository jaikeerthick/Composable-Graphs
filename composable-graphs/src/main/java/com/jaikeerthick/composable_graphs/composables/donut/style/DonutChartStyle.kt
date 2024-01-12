package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Stable
data class DonutChartStyle(
    val thickness: Dp = 60.dp,
    val sliceType: DonutSliceType = DonutSliceType.Normal,
    val colors: DonutChartColors = DonutChartColors(),
)


