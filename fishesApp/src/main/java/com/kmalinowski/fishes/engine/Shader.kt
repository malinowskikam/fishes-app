package com.kmalinowski.fishes.engine

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.kmalinowski.fishes.LOG_TAG
import com.kmalinowski.fishes.checkGLErrors

import com.kmalinowski.fishes.util.loadAssetAsText
import java.lang.RuntimeException

class Shader(context: Context, path: String, shaderType: Int) {
    var id: Int = GLES20.glCreateShader(shaderType)

    init {
        val shaderText = loadAssetAsText(context, path)!!
        GLES20.glShaderSource(id, shaderText)
        GLES20.glCompileShader(id)
        validateShader()
    }

    private fun validateShader() {
        val shaderStatus = IntArray(1)
        GLES20.glGetShaderiv(id, GLES20.GL_COMPILE_STATUS, shaderStatus, 0)
        if (shaderStatus[0] != GLES20.GL_TRUE) {
            Log.i(LOG_TAG, "Shader error: ${GLES20.glGetShaderInfoLog(id)}")
            throw RuntimeException("Shader does not compile!")
        }
        checkGLErrors()
    }
}