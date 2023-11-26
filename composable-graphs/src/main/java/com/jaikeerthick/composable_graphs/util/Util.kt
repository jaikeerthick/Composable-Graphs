package com.jaikeerthick.composable_graphs.util

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.BuildConfig

internal val DEFAULT_GRAPH_HEIGHT = 300.dp
internal val DEFAULT_GRAPH_PADDING = PaddingValues(horizontal = 10.dp)

private const val IS_LOGS_ON = true

internal fun logDebug(tag: String = "ComposableGraphs", message: String){
    if (BuildConfig.DEBUG && IS_LOGS_ON){
        Log.d(tag, message)
    }
}