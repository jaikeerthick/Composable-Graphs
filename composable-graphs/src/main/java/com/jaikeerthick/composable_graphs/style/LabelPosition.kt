package com.jaikeerthick.composable_graphs.style

/**
 * [LabelPosition] decides which position the labels of an axis should appear.
 * Currently supports: [RIGHT], [LEFT]
 */
sealed class LabelPosition{

    object RIGHT: LabelPosition()

    object LEFT: LabelPosition()

}
