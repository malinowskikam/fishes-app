package com.kmalinowski.fishes.engine

import android.opengl.GLES20
import android.opengl.Matrix

class Sprite(private val mesh: Mesh2d) {
    var position: FloatArray = floatArrayOf(0.0f, 0.0f)
    var rotation: Float = 0.0f
    var scale: FloatArray = floatArrayOf(1.0f, 1.0f)

    fun getModelMatrix(): FloatArray {
        val modelMatrix = FloatArray(16)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, position[0], position[1], 0.0f)
        Matrix.rotateM(modelMatrix, 0, rotation, 0.0f, 0.0f, 1.0f)
        Matrix.scaleM(modelMatrix, 0, scale[0], scale[1], 1.0f)
        return modelMatrix
    }

    fun draw(program: Program) {
        GLES20.glUseProgram(program.id)

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.vertexBufferId)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.indexBufferId)

        GLES20.glEnableVertexAttribArray(program.positionAttribute)
        GLES20.glEnableVertexAttribArray(program.uvAttribute)

        val posOffset = 0
        val uvOffset = Float.SIZE_BYTES * mesh.floatsPerVertex
        GLES20.glVertexAttribPointer(
            program.positionAttribute,
            mesh.floatsPerVertex,
            GLES20.GL_FLOAT,
            false,
            0,
            posOffset
        )
        GLES20.glVertexAttribPointer(
            program.uvAttribute,
            mesh.floatsPerUv,
            GLES20.GL_FLOAT,
            false,
            0,
            uvOffset
        )

        val modelMatrix = getModelMatrix()
        GLES20.glUniformMatrix4fv(program.modelMatrixLocation, 1, false, modelMatrix, 0)
        val vpMatrix = FloatArray(16)
        Matrix.orthoM(vpMatrix, 0, -2.0f, 2.0f, -2.0f, 2.0f, -1.0f, 1.0f)
        GLES20.glUniformMatrix4fv(program.vpMatrixLocation, 1, false, vpMatrix, 0)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.indicesCount * 3, GLES20.GL_UNSIGNED_INT, 0)

        GLES20.glDisableVertexAttribArray(program.positionAttribute)
        GLES20.glDisableVertexAttribArray(program.uvAttribute)

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)

        GLES20.glUseProgram(0)
    }
}