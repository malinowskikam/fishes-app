package com.kmalinowski.fishes.engine

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_NO_ERROR
import android.opengl.GLSurfaceView
import android.util.Log
import com.kmalinowski.fishes.constants.LOG_TAG
import com.kmalinowski.fishes.constants.targetFPS
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FishesRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private var program: Program? = null
    var frame = 0L
    var lastTime = System.nanoTime()
    var width = 0
    var height = 0
    var sprites: MutableList<Sprite> = mutableListOf()

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

        GLES20.glEnable(GLES20.GL_CULL_FACE)
        GLES20.glCullFace(GLES20.GL_BACK)

        program = Program(context, "shaders/simple.vertex.glsl", "shaders/simple.fragment.glsl")
        sprites.add(Sprite(SpriteMesh()))
        lastTime = System.nanoTime()
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        this.width = width
        this.height = height

//        val projectionMatrix = FloatArray(16)
//        Matrix.orthoM(
//            projectionMatrix,
//            0,
//            0.0f,
//            width.toFloat(),
//            0.0f,
//            height.toFloat(),
//            -1.0f,
//            1.0f
//        )
//
//        program!!.setProjectionViewMatrix(projectionMatrix)

        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        val currentTime = System.nanoTime()
        val frameLength = currentTime - lastTime
        val fps = 1000000000.0 / frameLength
        if (targetFPS > fps) {
            lastTime = currentTime
            frame++
            if (frame % 240 == 0L) {
                Log.i(LOG_TAG, String.format("fps: %f", fps))
            }
            update()
            draw()
        }
        GLES20.glFlush()
    }

    private fun update() {
        val sprite = sprites[0]
        sprite.rotation += 0.2f
    }

    private fun draw() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        for (s in sprites) {
            s.draw(program!!)
        }

        checkGLErrors()
    }
}

fun checkGLErrors() {
    val errorCode = GLES20.glGetError()
    if (errorCode != GL_NO_ERROR) {
        Log.i(LOG_TAG, "GL Error: $errorCode")
    }
}