package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Stable
data class DonutChartStyle(
    val thickness: Dp = 60.dp,
    val sliceType: DonutSliceType = DonutSliceType.Normal,
    val colors: DonutChartColors = DonutChartColors(),
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode? = null
)

@Stable
data class DonutChartColors(
    val inactiveProgressColor: Color = Color.LightGray,
)

