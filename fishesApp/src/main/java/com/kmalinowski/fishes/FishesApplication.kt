package com.kmalinowski.fishes

import android.app.Application
import android.util.Log

const val LOG_TAG = "APP"

class FishesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "Application started")
    }
}