package com.jaikeerthick.composable_graphs.composables.pie.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode


@Stable
data class PieChartColors(
    val labelColor: Color = Color.White,
    val percentageColor: Color = Color.White,
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DefaultBlendMode
)
