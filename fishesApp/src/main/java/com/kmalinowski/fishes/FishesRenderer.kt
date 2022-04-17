package com.kmalinowski.fishes

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_NO_ERROR
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.MotionEvent
import com.kmalinowski.fishes.engine.Program
import com.kmalinowski.fishes.scenes.Scene
import com.kmalinowski.fishes.util.getAngle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FishesRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var lastFrameTime = System.nanoTime()
    var targetFrameLength = 16666667L / 2L
    //var targetFrameLength = 16666667L
    //var targetFrameLength = 16666667`L * 2L

    private var lastTouchX: Float = 0.0f
    private var lastTouchY: Float = 0.0f

    private lateinit var scene: Scene
    private lateinit var program: Program

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        initializeGL()

        program = Program(context, "shaders/simple.vertex.glsl", "shaders/simple.fragment.glsl")
        scene = Scene(context)
        scene.initialize()
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        scene.surfaceWidth = width
        scene.surfaceHeight = height

        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        val currentFrameTime = System.nanoTime()
        drawFrame()
        scene.update(currentFrameTime)
        lastFrameTime = currentFrameTime
        val frameLength = System.nanoTime() - currentFrameTime
        val sleepMillis = ((targetFrameLength - frameLength).toDouble() * 10e-7).toLong()
        Thread.sleep(if (sleepMillis > 0L) sleepMillis else 0)
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("TEST", "$event")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x - scene.surfaceWidth.toFloat() / 2.0f
                lastTouchY = event.y - scene.surfaceHeight.toFloat() / 2.0f
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x - scene.surfaceWidth.toFloat() / 2.0f
                val y = event.y - scene.surfaceHeight.toFloat() / 2.0f
                scene.objects[0].rotation -= getAngle(lastTouchX, lastTouchY, x, y)
                lastTouchX = x
                lastTouchY = y
                return true
            }
            else -> return false
        }
    }

    private fun drawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        for (obj in scene.objects) {
            obj.draw(program, scene)
        }

        checkGLErrors()
        GLES20.glFlush()
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