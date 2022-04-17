package com.kmalinowski.fishes.util

import kotlin.math.atan2

val radToDegreeConst = 57.295779f

fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Float {
    return atan2(x1 * y2 - y1 * x2, x1 * x2 + y1 * y2)
}

