package com.jaikeerthick.composable_graphs.composables.pie.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke

@Stable
data class PieChartStyle(
    val stroke: Stroke? = null,
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode? = null
)
