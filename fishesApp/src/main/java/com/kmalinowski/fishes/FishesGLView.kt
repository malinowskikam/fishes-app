package com.kmalinowski.fishes

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent

class FishesGLView(context: Context, attrs: AttributeSet) : GLSurfaceView(context, attrs) {
    private val renderer = FishesRenderer(context)

    fun initGlContext() {
        this.setEGLContextClientVersion(2)
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        this.setRenderer(renderer)
    }

    @SuppressLint("ClickableViewAccessibility")
    // Touch event are forwarded to GL renderer
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return renderer.onTouchEvent(event)
    }
}