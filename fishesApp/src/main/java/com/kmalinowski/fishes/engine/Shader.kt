package com.kmalinowski.fishes.engine

import android.content.Context
import android.opengl.GLES31

import com.kmalinowski.fishes.util.loadAssetAsText

class Shader(context: Context, path: String, shaderType: Int) {
    var id: Int = GLES31.glCreateShader(shaderType)

    init{
        GLES31.glShaderSource(id, loadAssetAsText(context, path))
        GLES31.glCompileShader(id)
    }
}