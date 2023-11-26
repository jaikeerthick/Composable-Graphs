package com.jaikeerthick.composablegraphs

import androidx.lifecycle.ViewModel
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition

class MainActivityViewModel: ViewModel() {

    /**
     * Customizing [LineGraphStyle]
     */
    internal val lineGraphCustomStyle = LineGraphStyle(
        visibility = LineGraphVisibility(
            isYAxisLabelVisible = true,
            isCrossHairVisible = true,
            isGridVisible = true
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
        BarData(x = "22", y = 20),
        BarData(x = "23", y = 30),
        BarData(x = "24", y = 10),
        BarData(x = "25", y = 60),
        BarData(x = "26", y = 35),
    )

}