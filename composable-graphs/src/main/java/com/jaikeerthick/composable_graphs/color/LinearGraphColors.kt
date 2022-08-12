package com.jaikeerthick.composable_graphs.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class LinearGraphColors(
    val backgroundColor: Color = Color.Transparent,
    val lineColor: Color = GraphAccent,
    val pointColor: Color = GraphAccent,
    val clickHighlightColor: Color = PointHighlight,
    val crossHairColor: Color = Color.LightGray,
    /**
     * Doesn't support Color from Canvas library, Use native Color values
     */
    val xAxisTextColor: Int = android.graphics.Color.GRAY,
    /**
     * Doesn't support Color from Canvas library, Use native Color values
     */
    val yAxisTextColor: Int = android.graphics.Color.GRAY,

    val fillGradient: Brush? = null
)
