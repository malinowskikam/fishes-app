package com.kmalinowski.fishes

import android.app.Application
import android.util.Log
import com.kmalinowski.fishes.constants.LOG_TAG

class FishesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "Application started")
    }
}