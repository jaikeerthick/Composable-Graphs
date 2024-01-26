package com.jaikeerthick.composable_graphs.composables.donut.style

import androidx.compose.runtime.Stable

@Stable
sealed interface DonutSliceType{

    object Normal: DonutSliceType

    object Rounded: DonutSliceType
}