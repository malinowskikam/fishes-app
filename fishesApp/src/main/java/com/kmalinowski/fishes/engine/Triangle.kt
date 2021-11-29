package com.kmalinowski.fishes.engine

import android.opengl.GLES31
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle {
    private val cordsPerVertex = 3
    private val triangleCords = floatArrayOf(
        0.0f, 0.622008459f, 0.0f,
        -0.5f, -0.311004243f, 0.0f,
        0.5f, -0.311004243f, 0.0f
    )
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var vertexBuffer: FloatBuffer

    init {
        val bb = ByteBuffer.allocateDirect(triangleCords.size * 4).order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCords)
        vertexBuffer.position(0)
    }

    fun draw(program: Program) {
        GLES31.glUseProgram(program.id)
        val positionAttr = GLES31.glGetAttribLocation(program.id, "position")
        GLES31.glEnableVertexAttribArray(positionAttr)
        GLES31.glVertexAttribPointer(
            positionAttr,
            cordsPerVertex,
            GLES31.GL_FLOAT,
            false,
            cordsPerVertex * 4,
            vertexBuffer
        )
        val colorAttr = GLES31.glGetUniformLocation(program.id, "color")
        GLES31.glUniform4fv(colorAttr, 1, color, 0)
        GLES31.glDrawArrays(
            GLES31.GL_TRIANGLES,
            0,
            triangleCords.size / cordsPerVertex
        )
        GLES31.glDisableVertexAttribArray(positionAttr)
    }
}