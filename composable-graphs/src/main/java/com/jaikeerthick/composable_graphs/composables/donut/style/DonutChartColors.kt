package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode


@Stable
data class DonutChartColors(
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DefaultBlendMode
)