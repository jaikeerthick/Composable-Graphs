package com.jaikeerthick.composable_graphs.util

import kotlin.math.roundToInt

internal object GraphHelper {

    internal fun getAbsoluteMax(list: List<Number>): Number{
        return list.maxByOrNull {
            it.toFloat().roundToInt()
        } ?: 0
    }

    internal fun getAbsoluteMin(list: List<Number>): Number{
        return list.minByOrNull {
            it.toFloat().roundToInt()
        } ?: 0
    }


    internal fun roundMaxYPoint(point: Number): Number{

        // maxYPoint - the point gonna be rounded
        var maxYPoint = point

        if (maxYPoint.toInt() > 100) {
            maxYPoint = (maxYPoint.toFloat() / 50.0).roundToInt() * 50
            maxYPoint += 50

        }else{
            maxYPoint = (maxYPoint.toFloat() / 10.0).roundToInt() * 10
            maxYPoint += 10
        }
        return maxYPoint
    }
}