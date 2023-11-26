package com.jaikeerthick.composable_graphs.composables.line.style

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jaikeerthick.composable_graphs.theme.Gradient2
import com.jaikeerthick.composable_graphs.theme.GraphAccent
import com.jaikeerthick.composable_graphs.theme.PointHighlight


sealed interface LineGraphFillType {
    object None : LineGraphFillType
    class Solid(val color: Color): LineGraphFillType
    class Gradient(val brush: Brush): LineGraphFillType
}

data class LineGraphColors(
    // basics
    val lineColor: Color = GraphAccent,
    val pointColor: Color = GraphAccent,
    val clickHighlightColor: Color = PointHighlight,
    val crossHairColor: Color = Color.LightGray,
    // axis
    val xAxisTextColor: Color = Color.Gray,
    val yAxisTextColor: Color = Color.Gray,
    // fill
    val fillType: LineGraphFillType =
        LineGraphFillType.Gradient(brush = Brush.verticalGradient(listOf(GraphAccent, Gradient2)))
)
