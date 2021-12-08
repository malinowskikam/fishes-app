package com.kmalinowski.fishes.scenes

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.kmalinowski.fishes.engine.BitmapTexture
import com.kmalinowski.fishes.engine.DrawableObject
import com.kmalinowski.fishes.engine.SpriteMesh

class Scene(context: Context) {
    var frame = 0L
    var lastUpdate = 0L

    var windowWidth: Int = 0
    var windowHeight: Int = 0
    var pvMatrix = FloatArray(16)

    var objects: MutableList<DrawableObject> = mutableListOf()

    init {
        Matrix.setIdentityM(pvMatrix, 0)
        lastUpdate = System.nanoTime()
        objects.add(DrawableObject(SpriteMesh(), BitmapTexture(context, GLES20.GL_TEXTURE_2D, "textures/test.png")))
    }

    fun initialize() {

    }

    fun update() {
        objects[0].rotation += 0.8f

        frame++
        lastUpdate = System.nanoTime()
    }
}