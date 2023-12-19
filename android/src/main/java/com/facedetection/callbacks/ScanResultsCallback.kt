package com.facedetection.callbacks

import com.facedetection.helpers.ProcessImageResult
import com.google.mlkit.vision.face.Face

interface ScanResultsCallback {
    fun success(faces: List<Face>)
    fun cancel()
    fun error(exception: Exception?)
}
