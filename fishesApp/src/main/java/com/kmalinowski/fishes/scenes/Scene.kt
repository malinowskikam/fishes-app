package com.kmalinowski.fishes.scenes

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.kmalinowski.fishes.engine.BitmapTexture
import com.kmalinowski.fishes.engine.DrawableObject
import com.kmalinowski.fishes.engine.SpriteMesh

class Scene(context: Context) {
    var frame = 0L
    var lastUpdateTime = 0L

    var surfaceWidth: Int = 0
    var surfaceHeight: Int = 0
    var pvMatrix = FloatArray(16)

    var objects: MutableList<DrawableObject> = mutableListOf()
    private val androidContext = context

    init {
        Matrix.setIdentityM(pvMatrix, 0)
        lastUpdateTime = System.nanoTime()
    }

    fun initialize() {
        lastUpdateTime = System.nanoTime()
        objects.add(
            DrawableObject(
                SpriteMesh(),
                BitmapTexture(androidContext, GLES20.GL_TEXTURE_2D, "textures/image.jpg")
            )
        )
    }

    fun update(currentUpdateTime: Long) {
        //val sinceLastUpdate = (currentUpdateTime - lastUpdateTime).toFloat()

        frame++
        lastUpdateTime = currentUpdateTime
    }
}