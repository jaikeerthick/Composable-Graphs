package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color


@Stable
sealed interface DonutChartType {

    @Stable
    object Normal: DonutChartType

    @Stable
    data class Progressive(
        val totalProgress: Float = 100F,
        val progressInactiveColor: Color = Color.LightGray
    ) : DonutChartType
}