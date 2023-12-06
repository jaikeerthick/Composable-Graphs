package com.jaikeerthick.composable_graphs.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.round
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

internal object GraphHelper {

    internal fun getAbsoluteMax(list: List<Number>): Number {
        return list.maxByOrNull {
            it.toFloat().roundToInt()
        } ?: 0
    }

    internal fun getAbsoluteMin(list: List<Number>): Number {
        return list.minByOrNull {
            it.toFloat().roundToInt()
        } ?: 0
    }


    internal fun roundMaxYPoint(point: Number): Number {

        // maxYPoint - the point gonna be rounded
        var maxYPoint = point

        if (maxYPoint.toInt() > 100) {
            maxYPoint = (maxYPoint.toFloat() / 50.0).roundToInt() * 50
            maxYPoint += 50

        } else {
            maxYPoint = (maxYPoint.toFloat() / 10.0).roundToInt() * 10
            maxYPoint += 10
        }
        return maxYPoint
    }

    internal fun convertPercentageToDegree(percentage: Float): Float {
        // Desired Result: 0F..100F = 0F..360F
        return (percentage / 100F) * 360F
    }


    fun centerOf(offset1: Offset, offset2: Offset): Offset {
        val xOffset = (offset1.x + offset2.x) / 2
        val yOffset = (offset1.y + offset2.y) / 2
        return Offset(xOffset, yOffset)
    }

    internal fun DrawScope.getOffsetOfAngle(angle: Float, radius: Float): Offset {
        return Offset(
            x = (cos(Math.toRadians(angle.toDouble())) * radius + center.x).toFloat(),
            y = (sin(Math.toRadians(angle.toDouble())) * radius + center.y).toFloat()
        )
    }

    internal fun getAngleFromOffset(offset: Offset, radius: Float): Float {

        val angle = Math.toDegrees(
            atan2(
                offset.y - radius.toDouble(),
                offset.x - radius.toDouble()
            )
        )

        return angle.toFloat()
    }
}