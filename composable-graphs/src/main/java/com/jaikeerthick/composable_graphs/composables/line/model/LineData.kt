package com.jaikeerthick.composable_graphs.composables.line.model

/**
 * [LineData] is simply a point for [LineGraph].
 * @param x String that should be displayed in the x axis of this point
 * @param y Value, that the point should be plotted on the graph with respect to y-axis.
 */
data class LineData(
    val x: String,
    val y: Number
)