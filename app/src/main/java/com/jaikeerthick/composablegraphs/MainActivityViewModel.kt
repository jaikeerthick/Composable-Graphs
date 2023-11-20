package com.jaikeerthick.composablegraphs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.ViewModel
import com.jaikeerthick.composable_graphs.color.Gradient2
import com.jaikeerthick.composable_graphs.color.Gradient3
import com.jaikeerthick.composable_graphs.color.GraphAccent2
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.color.PointHighlight2
import com.jaikeerthick.composable_graphs.composables.BarData
import com.jaikeerthick.composable_graphs.composables.LineData
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility

class MainActivityViewModel: ViewModel() {

    /**
     * Customizing [LineGraphStyle] (Optional)
     */
    internal val lineGraphCustomStyle = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isYAxisLabelVisible = true,
            isCrossHairVisible = true,
            isGridVisible = true
        ),
        colors = LinearGraphColors(
            lineColor = GraphAccent2,
            pointColor = GraphAccent2,
            clickHighlightColor = PointHighlight2,
            fillGradient = Brush.verticalGradient(
                listOf(Gradient3, Gradient2)
            )
        ),
        yAxisLabelPosition = LabelPosition.LEFT
    )

    internal val lineGraphData = listOf(
        LineData(x = "Sun", y = 200),
        LineData(x = "Mon", y = 40),
        LineData(x = "Tues", y = 60),
        LineData(x = "Wed", y = 450),
        LineData(x = "Thur", y = 700),
        LineData(x = "Fri", y = 30),
        LineData(x = "Sat", y = 50),
    )

    internal val barGraphData = listOf(
        BarData(x = "22werifbwwwefwefwefewdfief", y = 20),
        BarData(x = "23", y = 30),
        BarData(x = "24", y = 10),
        BarData(x = "25", y = 60),
        BarData(x = "26", y = 35),
    )

}