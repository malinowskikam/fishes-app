package com.kmalinowski.fishes.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.common.io.CharStreams
import com.kmalinowski.fishes.LOG_TAG
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.io.InputStream

fun <T> loadAsset(context: Context, path: String, handleStream: (InputStream) -> T): T? {
    var loadedAsset: T? = null
    try {
        context.assets.open(path).use { stream ->
            loadedAsset = handleStream(stream)
        }
    } catch (e: IOException) {
        Log.e(LOG_TAG, "Loading asset failed: $path", e)
    }
    return loadedAsset
}

fun loadAssetAsText(context: Context, path: String): String? {
    return loadAsset(context, path) { stream ->
        CharStreams.toString(
            InputStreamReader(
                stream,
                StandardCharsets.UTF_8
            )
        )
    }
}

fun loadAssetAsBitmap(context: Context, path: String, options: BitmapFactory.Options): Bitmap? {
    return loadAsset(context, path) { stream ->
        BitmapFactory.decodeStream(stream, null, options)
    }
}