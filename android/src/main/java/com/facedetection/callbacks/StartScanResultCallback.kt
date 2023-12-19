package com.facedetection.callbacks

public interface StartScanResultCallback {
    fun success()
    fun error(exception: Exception?)
}
