package com.jaikeerthick.composablegraphs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphVisibility
import com.jaikeerthick.composable_graphs.composables.donut.DonutChart
import com.jaikeerthick.composable_graphs.composables.donut.model.DonutData
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartStyle
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutChartType
import com.jaikeerthick.composable_graphs.composables.donut.style.DonutSliceType
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composablegraphs.theme.GraphAccent2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {

    MaterialTheme {

        val context = LocalContext.current
        val viewModel = remember { MainActivityViewModel() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {

                var lineGraphClickedValue by remember { mutableStateOf("") }

                Row(
                    modifier = Modifier
                        .padding(all = 25.dp)
                ) {
                    Text(text = "Value: ", color = Color.Gray)
                    Text(
                        text = lineGraphClickedValue,
                        color = GraphAccent2,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                LineGraph(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    data = viewModel.lineGraphData,
                    style = viewModel.lineGraphCustomStyle,
                    onPointClick = { value ->
                        lineGraphClickedValue = "${value.x}, ${value.y}"
                    },
                )
            }

            val previewData = remember {
                listOf(
                    PieData(value = 130F, label = "HTC"),
                    PieData(value = 260F, label = "Apple"),
                    PieData(value = 500F, label = "Google"),
                )
            }

            val donutPreviewData = remember {
                listOf(
                    DonutData(value = 30F),
                    DonutData(value = 60F),
                    DonutData(value = 70F),
                    DonutData(value = 50F),
                )
            }

            DonutChart(
                data = donutPreviewData,
                type = DonutChartType.Normal,
                style = DonutChartStyle(
                    sliceType = DonutSliceType.Rounded,
                    thickness = 30.dp
                ),
                onSliceClick = { donutData ->
                    println("JAIKKK -- onClick: ${donutData.value}")
                }
            )

//            PieChart(
//                modifier = Modifier.size(170.dp),
//                data = previewData,
//                style = PieChartStyle(
//                    visibility = PieChartVisibility(
//                        isLabelVisible = true,
//                        isPercentageVisible = true
//                    )
//                ),
//                onSliceClick = { pieData ->
//                    Toast.makeText(context, "${pieData.label}", Toast.LENGTH_SHORT).show()
//                }
//            )
        }
    }
}

@Preview
@Composable
fun MainAppPreview() {
    MainApp()
}
