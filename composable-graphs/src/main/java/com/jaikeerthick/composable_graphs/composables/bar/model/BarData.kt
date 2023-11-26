package com.jaikeerthick.composable_graphs.composables.bar.model

/**
 * [BarData] is simply a single bar value for [BarGraph].
 * @param x String that should be displayed in the x axis of this bar
 * @param y Value, the point should be plotted on the graph with respect to y-axis. Or simply
 * the height of the bar.
 */
data class BarData(
    val x: String,
    val y: Number
)