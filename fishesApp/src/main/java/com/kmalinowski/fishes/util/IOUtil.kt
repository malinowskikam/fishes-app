package com.kmalinowski.fishes.util

import android.content.Context
import android.util.Log
import com.google.common.io.CharStreams
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import com.kmalinowski.fishes.constants.LOG_TAG

fun loadAssetAsText(context: Context, path: String): String? {
    var text: String? = null
    try {
        context.assets.open(path).use { stream ->
            text = CharStreams.toString(InputStreamReader(stream, StandardCharsets.UTF_8))
        }
    } catch (e: IOException) {
        Log.e(LOG_TAG, String.format("Loading asset failed: %s", path), e)
    } finally {
        return text
    }
}