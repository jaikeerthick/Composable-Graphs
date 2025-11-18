package com.jaikeerthick.composable_graphs.util

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.BuildConfig

internal val DEFAULT_GRAPH_SIZE = 300.dp
internal val DEFAULT_GRAPH_PADDING = PaddingValues(horizontal = 10.dp)

private const val IS_LOGS_ON = false

internal fun logDebug(tag: String = "ComposableGraphs", message: String){
    if (BuildConfig.DEBUG && IS_LOGS_ON){
        Log.d(tag, message)
    }
}

internal fun getRandomColor(): Color {
    return Color(
        red = (0..255).random(),
        blue =  (0..255).random(),
        green =  (0..255).random()
    )
}