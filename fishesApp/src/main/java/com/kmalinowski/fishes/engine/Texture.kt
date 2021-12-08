package com.kmalinowski.fishes.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.opengl.GLES20
import android.opengl.GLUtils
import com.kmalinowski.fishes.checkGLErrors
import com.kmalinowski.fishes.util.loadAssetAsBitmap


abstract class Texture {
    val id: Int
    abstract val textureType: Int

    protected abstract val minFilterMode: Int
    protected abstract val magFilterMode: Int

    protected abstract val wrapSMode: Int
    protected abstract val wrapTMode: Int

    init {
        val indices = IntArray(1)
        GLES20.glGenTextures(1, indices, 0)
        id = indices[0]
    }

    protected fun setFilteringAndWrapping() {
        GLES20.glBindTexture(textureType, id)
        GLES20.glGenerateMipmap(textureType)
        GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MIN_FILTER, minFilterMode)
        GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_MAG_FILTER, magFilterMode)
        GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_S, wrapSMode)
        GLES20.glTexParameteri(textureType, GLES20.GL_TEXTURE_WRAP_T, wrapTMode)
        GLES20.glBindTexture(textureType, 0)
    }
}

class BitmapTexture(context: Context, override val textureType: Int, assetPath: String) :
    Texture() {
    companion object {
        private val bitmapOptions: BitmapFactory.Options = BitmapFactory.Options()

        init {
            bitmapOptions.inScaled = false
        }
    }

    override val minFilterMode: Int = GLES20.GL_LINEAR
    override val magFilterMode: Int = GLES20.GL_LINEAR

    override val wrapSMode: Int = GLES20.GL_REPEAT
    override val wrapTMode: Int = GLES20.GL_REPEAT

    init {
        val transformation = Matrix()
        transformation.postScale(-1f, -1f)
        transformation.postRotate(-90.0f)
        val inputBitmap: Bitmap = loadAssetAsBitmap(context, assetPath, bitmapOptions)!!
        val transformedBitmap = Bitmap.createBitmap(
            inputBitmap,
            0,
            0,
            inputBitmap.width,
            inputBitmap.height,
            transformation,
            true
        )
        inputBitmap.recycle()
        GLES20.glBindTexture(textureType, id)
        GLUtils.texImage2D(textureType, 0, transformedBitmap, 0)
        GLES20.glBindTexture(textureType, 0)
        setFilteringAndWrapping()
        transformedBitmap.recycle()

        checkGLErrors()
    }
}