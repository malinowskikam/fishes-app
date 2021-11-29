package com.kmalinowski.fishes.engine

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Square {
    val COORDS_PER_VERTEX = 3
    private var squareCords = floatArrayOf(
        -0.5f, 0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.5f, 0.5f, 0.0f
    )
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3)

    var vertexBuffer: FloatBuffer
    var drawListBuffer: ShortBuffer

    init {
        val bb = ByteBuffer.allocateDirect(squareCords.size * 4).order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(squareCords)
        vertexBuffer.position(0)

        val dlb = ByteBuffer.allocateDirect(drawOrder.size * 2).order(ByteOrder.nativeOrder())
        drawListBuffer = dlb.asShortBuffer()
        drawListBuffer.put(drawOrder)
        drawListBuffer.position(0)
    }
}