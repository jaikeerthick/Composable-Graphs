package com.jaikeerthick.composable_graphs.composables.bar.style

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jaikeerthick.composable_graphs.theme.Gradient2
import com.jaikeerthick.composable_graphs.theme.GraphAccent
import com.jaikeerthick.composable_graphs.theme.PointHighlight

sealed interface BarGraphFillType {
    class Solid(val color: Color): BarGraphFillType
    class Gradient(val brush: Brush): BarGraphFillType
}

data class BarGraphColors(

    val clickHighlightColor: Color = PointHighlight,
    // axis
    val xAxisTextColor: Color = Color.Gray,
    val yAxisTextColor: Color = Color.Gray,
    // fill
    val fillType: BarGraphFillType =
        BarGraphFillType.Gradient(brush = Brush.verticalGradient(listOf(GraphAccent, Gradient2)))
)
