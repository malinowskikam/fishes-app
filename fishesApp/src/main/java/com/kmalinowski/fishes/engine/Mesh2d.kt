package com.kmalinowski.fishes.engine

import android.opengl.GLES20
import java.nio.*

abstract class Mesh {
    abstract val vertexBufferId: Int
    abstract val uvBufferId: Int
    abstract val indexBufferId: Int

    abstract val indicesCount: Int
    abstract val floatsPerVertex: Int
    abstract val floatsPerUv: Int
    abstract val vertexStride: Int

    protected fun calcVertexStride(): Int {
        return Float.SIZE_BYTES * floatsPerVertex + Float.SIZE_BYTES * floatsPerUv
    }

    protected fun createBuffer(floatArray: FloatArray): FloatBuffer {
        val fb: FloatBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * floatArray.size).order(
            ByteOrder.nativeOrder()
        ).asFloatBuffer()
        fb.put(floatArray)
        fb.position(0)
        return fb
    }

    protected fun createBuffer(intArray: IntArray): IntBuffer {
        val ib: IntBuffer = ByteBuffer.allocateDirect(Int.SIZE_BYTES * intArray.size).order(
            ByteOrder.nativeOrder()
        ).asIntBuffer()
        ib.put(intArray)
        ib.position(0)
        return ib
    }

    protected fun createBufferObject(bufferType: Int, bufferId: Int, buffer: Buffer, size: Int) {
        GLES20.glBindBuffer(bufferType, bufferId)
        GLES20.glBufferData(bufferType, size, buffer, GLES20.GL_STATIC_DRAW)
        GLES20.glBindBuffer(bufferType, 0)
    }
}

open class Mesh2d(vertices: FloatArray, uvs: FloatArray, indices: IntArray) : Mesh() {
    final override val vertexBufferId: Int
    final override val uvBufferId: Int
    final override val indexBufferId: Int

    final override val indicesCount: Int = indices.size
    final override val vertexStride: Int
    override val floatsPerVertex = 2
    override val floatsPerUv = 2

    init {
        val buffers = IntArray(3)
        GLES20.glGenBuffers(3, buffers, 0)

        vertexBufferId = buffers[0]
        uvBufferId = buffers[1]
        indexBufferId = buffers[2]

        createBufferObject(
            GLES20.GL_ARRAY_BUFFER,
            vertexBufferId,
            createBuffer(vertices),
            vertices.size * 4
        )

        createBufferObject(
            GLES20.GL_ARRAY_BUFFER,
            uvBufferId,
            createBuffer(uvs),
            uvs.size * 4
        )

        vertexStride = calcVertexStride()

        createBufferObject(
            GLES20.GL_ELEMENT_ARRAY_BUFFER,
            indexBufferId,
            createBuffer(indices),
            indices.size * 4
        )
    }
}

val spriteMeshVertices = floatArrayOf(
    -1.0f, -1.0f,
    -1.0f, 1.0f,
    1.0f, 1.0f,
    1.0f, -1.0f,
)

val spriteMeshUvs = floatArrayOf(
    0.0f, 0.0f,
    0.0f, 1.0f,
    1.0f, 1.0f,
    1.0f, 0.0f,
)

val spriteMeshIndices = intArrayOf(
    0, 3, 2, 0, 2, 1
)

class SpriteMesh : Mesh2d(spriteMeshVertices, spriteMeshUvs, spriteMeshIndices)
