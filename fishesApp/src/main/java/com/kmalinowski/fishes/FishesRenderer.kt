package com.kmalinowski.fishes

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_NO_ERROR
import android.opengl.GLSurfaceView
import android.util.Log
import com.kmalinowski.fishes.engine.Program
import com.kmalinowski.fishes.scenes.Scene
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FishesRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var lastFrame = System.nanoTime()
    var targetFps = 60.0f

    private lateinit var scene: Scene
    private lateinit var program: Program

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        initializeGL()

        program = Program(context, "shaders/simple.vertex.glsl", "shaders/simple.fragment.glsl")
        scene = Scene(context)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        scene.windowWidth = width
        scene.windowHeight = height

        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        val currentTime = System.nanoTime()
        val frameLength = currentTime - lastFrame
        val fps = 1000000000.0 / frameLength
        if (targetFps > fps) {
            lastFrame = currentTime

            scene.update()
            draw()
        }
        GLES20.glFlush()
    }

    private fun draw() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        for (obj in scene.objects) {
            obj.draw(program, scene)
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

fun initializeGL() {
    GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

    GLES20.glEnable(GLES20.GL_CULL_FACE)
    GLES20.glEnable(GLES20.GL_BLEND)
    GLES20.glCullFace(GLES20.GL_BACK)

    GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
}