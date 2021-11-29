package com.kmalinowski.fishes.engine

import android.content.Context
import android.opengl.GLES31

class Program(context: Context, vertexShaderPath: String, fragmentShaderPath: String) {
    private val vertexShader: Shader = Shader(context, vertexShaderPath, GLES31.GL_VERTEX_SHADER)
    private val fragmentShader: Shader = Shader(context, fragmentShaderPath, GLES31.GL_FRAGMENT_SHADER)
    val id: Int = GLES31.glCreateProgram()

    init {
        GLES31.glAttachShader(id, vertexShader.id)
        GLES31.glAttachShader(id, fragmentShader.id)
        GLES31.glLinkProgram(id)
    }
}