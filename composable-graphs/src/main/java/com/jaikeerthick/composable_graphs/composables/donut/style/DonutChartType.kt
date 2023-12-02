package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable


@Stable
sealed interface DonutChartType{

    object Normal: DonutChartType

    data class Progressive(val totalProgress: Float = 100F) : DonutChartType
}