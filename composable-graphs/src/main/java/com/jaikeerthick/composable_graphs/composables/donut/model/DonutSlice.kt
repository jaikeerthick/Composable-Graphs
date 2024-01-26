package com.jaikeerthick.composable_graphs.composables.donut.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import java.util.UUID

@Stable
internal data class DonutSlice(
    val id: String,
    val startAngle: Float,
    val endAngle: Float,
    val color: Color
)