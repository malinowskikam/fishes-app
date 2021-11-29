package com.kmalinowski.fishes

import android.content.Context
import android.opengl.GLES31
import android.opengl.GLSurfaceView
import android.util.Log
import com.kmalinowski.fishes.constants.LOG_TAG
import com.kmalinowski.fishes.constants.targetFPS
import com.kmalinowski.fishes.engine.Program
import com.kmalinowski.fishes.engine.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FishesRenderer(context: Context): GLSurfaceView.Renderer {
    var program: Program = Program(context, "shaders/simple.vertex.glsl", "shaders/simple.fragment.glsl")
    var frame = 0L
    var lastTime = System.nanoTime()

    val triangle = Triangle()

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        GLES31.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

        GLES31.glEnable(GLES31.GL_CULL_FACE)
        GLES31.glCullFace(GLES31.GL_BACK)

        lastTime = System.nanoTime()
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
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
            drawFrame()
        }
        GLES31.glFlush()
    }

    private fun drawFrame() {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT or GLES31.GL_DEPTH_BUFFER_BIT)
        triangle.draw(program)
    }
}