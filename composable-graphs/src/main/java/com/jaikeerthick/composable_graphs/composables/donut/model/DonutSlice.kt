package com.jaikeerthick.composable_graphs.composables.donut.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
internal data class DonutSlice(
    val startAngle: Float,
    val endAngle: Float,
    val color: Color
)