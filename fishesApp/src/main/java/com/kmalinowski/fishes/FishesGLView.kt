package com.kmalinowski.fishes

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class FishesGLView(context: Context, attrs: AttributeSet) : GLSurfaceView(context, attrs) {
    fun initGlContext() {
        this.setEGLContextClientVersion(2)
        this.setEGLConfigChooser(8,8,8,8,16,0)
        this.setRenderer(FishesRenderer(context))
    }

}