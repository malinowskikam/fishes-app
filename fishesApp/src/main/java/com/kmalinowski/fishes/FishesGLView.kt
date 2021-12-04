package com.kmalinowski.fishes

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.kmalinowski.fishes.engine.FishesRenderer

class FishesGLView(context: Context, attrs: AttributeSet) : GLSurfaceView(context, attrs) {
    init {
        systemUiVisibility = (SYSTEM_UI_FLAG_LOW_PROFILE
                or SYSTEM_UI_FLAG_FULLSCREEN
                or SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}