package com.jaikeerthick.composablegraphs

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphColors
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
import com.jaikeerthick.composablegraphs.theme.BackgroundColor
import com.jaikeerthick.composablegraphs.theme.BackgroundColor2
import com.jaikeerthick.composablegraphs.theme.GraphAccent2
import com.jaikeerthick.composablegraphs.theme.PrimaryColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
private fun MainApp() {

    val viewModel = remember { MainActivityViewModel() }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Composable Graphs")
                    },
                    backgroundColor = PrimaryColor,
                )
            },
            backgroundColor = BackgroundColor,
            content = {
                BodyContent(
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
fun BodyContent(
    viewModel: MainActivityViewModel
) {

    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(all = 30.dp),
        content = {
            item(key = "item-0") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    backgroundColor = BackgroundColor2,
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            PieChart(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .size(220.dp),
                                data = viewModel.pieChartData,
                                style = viewModel.pieChartStyle,
                                onSliceClick = { pieData ->
                                    Toast.makeText(context, "${pieData.label}", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    }
                )
            }

            item(key = "item-1") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    backgroundColor = BackgroundColor2,
                    content = {
                        LineGraph(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                            data = viewModel.lineGraphData,
                            style = viewModel.lineGraphCustomStyle,
                            onPointClick = { value ->

                            },
                        )
                    }
                )
            }

            item(key = "item-2") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    backgroundColor = BackgroundColor2,
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            DonutChart(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .size(220.dp),
                                data = viewModel.donutChartData,
                                style = viewModel.donutChartStyle,
                            )
                        }
                    }
                )
            }

            item(key = "item-3") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    backgroundColor = BackgroundColor2,
                    content = {
                        BarGraph(
                            modifier = Modifier
                                .padding(30.dp),
                            data = viewModel.barGraphData,
                        )
                    }
                )
            }

            item(key = "item-4") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    backgroundColor = BackgroundColor2,
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            DonutChart(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .size(220.dp),
                                type = DonutChartType.Progressive(),
                                data = viewModel.donutChartData,
                                style = viewModel.donutChartStyle2,
                            )
                        }
                    }
                )
            }

        }
    )
}

@Preview
@Composable
fun MainAppPreview() {
    MainApp()
}
