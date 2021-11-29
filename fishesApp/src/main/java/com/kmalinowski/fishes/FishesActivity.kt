package com.kmalinowski.fishes

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kmalinowski.fishes.constants.LOG_TAG

class FishesActivity: AppCompatActivity() {
    private var fishesGLView: FishesGLView? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_fishes)

        val actionBar = supportActionBar
        actionBar?.hide()

        fishesGLView = findViewById(R.id.fishes_gl_view)
        fishesGLView?.setOnClickListener { Log.i(LOG_TAG, "Click") }
    }
}