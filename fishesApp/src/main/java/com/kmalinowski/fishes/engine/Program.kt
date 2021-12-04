package com.kmalinowski.fishes.engine

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.kmalinowski.fishes.constants.LOG_TAG

class Program(context: Context, vertexShaderPath: String, fragmentShaderPath: String) {
    private val vertexShader: Shader = Shader(context, vertexShaderPath, GLES20.GL_VERTEX_SHADER)
    private val fragmentShader: Shader = Shader(context, fragmentShaderPath, GLES20.GL_FRAGMENT_SHADER)
    val id: Int = GLES20.glCreateProgram()

    val positionAttribute: Int
    val uvAttribute: Int
    val colorLocation: Int
    val vpMatrixLocation: Int
    val modelMatrixLocation: Int

    init {
        GLES20.glAttachShader(id, vertexShader.id)
        GLES20.glAttachShader(id, fragmentShader.id)
        GLES20.glLinkProgram(id)
        validateProgram()

        GLES20.glUseProgram(id)
        positionAttribute = GLES20.glGetAttribLocation(id, "position")
        uvAttribute = GLES20.glGetAttribLocation(id, "uv")
        colorLocation = GLES20.glGetUniformLocation(id, "color")
        vpMatrixLocation = GLES20.glGetUniformLocation(id, "vpMatrix")
        modelMatrixLocation = GLES20.glGetUniformLocation(id, "modelMatrix")
        GLES20.glUseProgram(0)
    }

    private fun validateProgram() {
        val programStatus = IntArray(1)
        GLES20.glGetProgramiv(id, GLES20.GL_LINK_STATUS, programStatus,0)
        if(programStatus[0] != GLES20.GL_TRUE) {
            Log.i(LOG_TAG, "Program error: ${GLES20.glGetProgramInfoLog(id)}")
        }
    }
}