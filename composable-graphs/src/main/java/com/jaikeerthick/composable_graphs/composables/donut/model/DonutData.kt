package com.jaikeerthick.composable_graphs.composables.donut.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.jaikeerthick.composable_graphs.util.getRandomColor


@Stable
data class DonutData(
    val value: Float,
    val color: Color = getRandomColor(),
    val label: String? = null,
    val labelColor: Color? = null
)
