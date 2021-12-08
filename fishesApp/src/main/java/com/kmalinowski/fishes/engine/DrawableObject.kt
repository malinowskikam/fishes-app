package com.kmalinowski.fishes.engine

import android.opengl.GLES20
import android.opengl.Matrix
import com.kmalinowski.fishes.scenes.Scene

class DrawableObject(private val mesh: Mesh, private val texture: Texture) {
    var position: FloatArray = floatArrayOf(0.0f, 0.0f)
    var rotation: Float = 0.0f
    var scale: FloatArray = floatArrayOf(1.0f, 1.0f)

    fun draw(program: Program, scene: Scene) {
        GLES20.glUseProgram(program.id)

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.indexBufferId)

        GLES20.glEnableVertexAttribArray(program.positionAttribute)
        GLES20.glEnableVertexAttribArray(program.uvAttribute)

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.vertexBufferId)
        GLES20.glVertexAttribPointer(
            program.positionAttribute,
            mesh.floatsPerVertex,
            GLES20.GL_FLOAT,
            false,
            0,
            0
        )

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.uvBufferId)
        GLES20.glVertexAttribPointer(
            program.uvAttribute,
            mesh.floatsPerUv,
            GLES20.GL_FLOAT,
            false,
            0,
            0
        )

        val modelMatrix = getModelMatrix()
        GLES20.glUniformMatrix4fv(program.modelMatrixLocation, 1, false, modelMatrix, 0)

        val vpMatrix = FloatArray(16)

        GLES20.glUniformMatrix4fv(program.vpMatrixLocation, 1, false, scene.pvMatrix, 0)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(texture.textureType, texture.id)
        GLES20.glUniform1i(program.textureSamplerLocation, 0)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.indicesCount, GLES20.GL_UNSIGNED_INT, 0)

        GLES20.glDisableVertexAttribArray(program.positionAttribute)
        GLES20.glDisableVertexAttribArray(program.uvAttribute)

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)

        GLES20.glUseProgram(0)
    }

    private fun getModelMatrix(): FloatArray {
        val modelMatrix = FloatArray(16)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, position[0], position[1], 0.0f)
        Matrix.rotateM(modelMatrix, 0, rotation, 0.0f, 0.0f, 1.0f)
        Matrix.scaleM(modelMatrix, 0, scale[0], scale[1], 1.0f)
        return modelMatrix
    }
}