package com.jaikeerthick.composable_graphs.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class BarGraphColors(

    val backgroundColor: Color = Color.Transparent,
    val clickHighlightColor: Color = PointHighlight,
    /**
     * Doesn't support Color from Canvas library, Use native Color values
      */
    val xAxisTextColor: Int = android.graphics.Color.GRAY,
    /**
     * Doesn't support Color from Canvas library, Use native Color values
     */
    val yAxisTextColor: Int = android.graphics.Color.GRAY,
    val fillGradient: Brush? = null,

    )
