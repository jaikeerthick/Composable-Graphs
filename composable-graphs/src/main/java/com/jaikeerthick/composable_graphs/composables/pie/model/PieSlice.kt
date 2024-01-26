package com.jaikeerthick.composable_graphs.composables.pie.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.jaikeerthick.composable_graphs.util.getRandomColor
import java.util.UUID

@Stable
internal data class PieSlice(
    val id: String,
    val startAngle: Float,
    val startOffset: Offset,
    val endAngle: Float,
    val endOffset: Offset,
    val percentage: String,
    val color: MutableState<Color>,
    val label: String? = null,
    val labelColor: Color? = null
)
