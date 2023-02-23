package com.jaikeerthick.composablegraphs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.*
import com.jaikeerthick.composable_graphs.composables.BarGraph
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val style = BarGraphStyle(
                    visibility = BarGraphVisibility(
                        isYAxisLabelVisible = true
                    )
                )
                val style2 = LineGraphStyle(
                    visibility = LinearGraphVisibility(
                        isHeaderVisible = true,
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
                    yAxisLabelPosition = LabelPosition.RIGHT
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Box(modifier = Modifier.fillMaxWidth()) {

                        val clickedValue: MutableState<Pair<Any, Any>?> =
                            remember { mutableStateOf(null) }

                        LineGraph(
                            xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
                                GraphData.String(it)
                            },
                            yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
                            style = style2,
                            onPointClicked = {
                                clickedValue.value = it
                            },
                        )

                        clickedValue.value?.let {
                            Row(
                                modifier = Modifier
                                    .padding(all = 25.dp)
                            ) {
                                Text(text = "Value: ", color = Color.Gray)
                                Text(
                                    text = "${it.first}, ${it.second}",
                                    color = GraphAccent2,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                    }

                    BarGraph(
                        dataList = listOf(20, 30, 10, 60, 35),
                        header = {
                            Column {
                                Text(
                                    text = "This is Bar Graph",
                                    color = GraphAccent,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "With custom Header",
                                    color = Color.Gray
                                )
                            }
                        },
                        style = style
                    )


                }
            }

        }

    }
}
