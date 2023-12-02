package com.jaikeerthick.composable_graphs.composables.pie.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
internal data class PieSlice(
    val startAngle: Float,
    val endAngle: Float,
    val color: Color
)
