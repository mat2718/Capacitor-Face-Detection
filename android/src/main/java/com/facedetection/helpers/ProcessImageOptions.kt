package com.facedetection.helpers

import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.core.CameraSelector.LensFacing
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetectorOptions

// face scan options!!
class ProcessImageOptions(
    @FaceDetectorOptions.PerformanceMode performanceMode: Int,
    @FaceDetectorOptions.LandmarkMode landmarkMode: Int,
    @FaceDetectorOptions.ContourMode contourMode: Int,
    @FaceDetectorOptions.ClassificationMode classificationMode: Int,
    minFaceSize: Float,
    enableTracking: Boolean,
    inputImage: InputImage?,
    @LensFacing lensFacing: Int?
) {
    private var inputImage: InputImage? = null

    @FaceDetectorOptions.PerformanceMode
    val performanceMode: Int

    @FaceDetectorOptions.LandmarkMode
    val landmarkMode: Int

    @FaceDetectorOptions.ContourMode
    val contourMode: Int

    @FaceDetectorOptions.ClassificationMode
    val classificationMode: Int
    val minFaceSize: Float
    val isTrackingEnabled: Boolean

    @LensFacing
    var lensFacing: Int? = null

    init {
        if (inputImage != null) {
            this.inputImage = inputImage
        }
        this.performanceMode = performanceMode
        this.landmarkMode = landmarkMode
        this.contourMode = contourMode
        this.classificationMode = classificationMode
        this.minFaceSize = minFaceSize
        isTrackingEnabled = enableTracking
        this.lensFacing = lensFacing
    }

    fun getInputImage(): InputImage? {
        return inputImage
    }

}
