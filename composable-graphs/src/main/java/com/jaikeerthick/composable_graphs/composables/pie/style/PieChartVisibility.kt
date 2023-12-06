package com.jaikeerthick.composable_graphs.composables.pie.style

import androidx.compose.runtime.Stable


@Stable
data class PieChartVisibility(
    val isLabelVisible: Boolean = false,
    val isPercentageVisible: Boolean = false,
)
