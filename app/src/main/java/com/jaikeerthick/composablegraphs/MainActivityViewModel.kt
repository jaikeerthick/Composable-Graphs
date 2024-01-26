package com.jaikeerthick.composablegraphs

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.donut.model.DonutData
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartStyle
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutSliceType
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composablegraphs.theme.GraphAccent2

class MainActivityViewModel : ViewModel() {

    /**
     * Customizing [LineGraphStyle]
     */
    internal val lineGraphCustomStyle = LineGraphStyle(
        visibility = LineGraphVisibility(
            isYAxisLabelVisible = true,
            isCrossHairVisible = true,
            isGridVisible = true
        ),
        yAxisLabelPosition = LabelPosition.LEFT,
        colors = LineGraphColors(
            lineColor = GraphAccent2,
            pointColor = GraphAccent2,
            clickHighlightColor = GraphAccent2.copy(alpha = 0.5F),
            fillType = LineGraphFillType.Gradient(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GraphAccent2, Color.Transparent
                    )
                )
            )
        )
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

    val pieChartData = listOf(
        PieData(value = 130F, label = "HTC"),
        PieData(value = 260F, label = "Apple"),
        PieData(value = 500F, label = "Google"),
    )

    val pieChartStyle = PieChartStyle(
        visibility = PieChartVisibility(
            isLabelVisible = true,
            isPercentageVisible = true
        ),
        labelSize = 10.sp
    )

    val donutChartData = listOf(
        DonutData(value = 30F),
        DonutData(value = 60F),
        DonutData(value = 70F),
        DonutData(value = 50F),
    )

    val donutChartStyle = DonutChartStyle(
        thickness = 50.dp,
        sliceType = DonutSliceType.Rounded,
    )

    val donutChartStyle2 = DonutChartStyle(
        thickness = 50.dp,
        sliceType = DonutSliceType.Normal,
    )

}