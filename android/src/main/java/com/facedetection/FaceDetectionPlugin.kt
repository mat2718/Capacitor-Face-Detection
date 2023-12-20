package com.facedetection

import android.Manifest
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.camera.core.CameraSelector
import com.facedetection.callbacks.ScanImageResultsCallback
import com.facedetection.callbacks.StartScanResultCallback
import com.facedetection.helpers.ProcessImageOptions
import com.facedetection.helpers.ProcessImageResult
import com.getcapacitor.JSObject
import com.getcapacitor.Logger
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.Permission
import com.getcapacitor.annotation.PermissionCallback
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetectorOptions

@CapacitorPlugin(
    name = "FaceDetectionPlugin", permissions = [Permission(
        strings = [Manifest.permission.CAMERA],
        alias = FaceDetectionPlugin.CAMERA
    )]
)
class FaceDetectionPlugin : Plugin() {
    private var implementation: FaceDetection? = null
    override fun load() {
        try {
            implementation = FaceDetection(this)
        } catch (exception: Exception) {
            Logger.error(TAG, exception.message, exception)
        }
    }

    @PluginMethod
    fun startActiveScan(call: PluginCall) {
        try {
            val performanceMode: Int? =
                call.getInt("performanceMode", FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            val landmarkMode: Int? =
                call.getInt("landmarkMode", FaceDetectorOptions.LANDMARK_MODE_NONE)
            val contourMode: Int? =
                call.getInt("contourMode", FaceDetectorOptions.CONTOUR_MODE_NONE)
            val classificationMode: Int? =
                call.getInt("classificationMode", FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            val minFaceSize: Float? = call.getFloat("minFaceSize", 0.1f)
            val enableTracking: Boolean = call.getBoolean("enableTracking", false) == true
            val lensFacingOption: String? = call.getString("lensFacing", "BACK")
            val lensFacing: Int =
                if (lensFacingOption == "FRONT") CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
            val options = performanceMode?.let {
//            checking for null now. ill probably set a default config later
                if (landmarkMode != null && contourMode != null && classificationMode != null && minFaceSize != null) {
                    ProcessImageOptions(
                        it,
                        landmarkMode,
                        contourMode,
                        classificationMode,
                        minFaceSize,
                        enableTracking,
                        null,
                        lensFacing
                    )
                } else {
                    call.reject(ERROR_MISSING_PARAM)
                    return
                }
            }
            val granted = implementation?.requestCameraPermissionIfNotDetermined(call)
            if (granted == false) {
                return
            }
            activity
                .runOnUiThread {
                    if (options != null) {
                        implementation?.startScan(
                            options,
                            object : StartScanResultCallback {
                                override fun success() {
                                    call.resolve()
                                }
                                override fun error(exception: Exception?) {
                                    val message = exception?.message
                                    Logger.error(TAG, message, exception)
                                    call.reject(message)
                                }
                            }
                        )
                    }
                }
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(TAG, message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun readFaceFromImage(call: PluginCall) {
//        used to process static image
        try {
            val path: String? = call.getString("path", null)
            if (path == null) {
                call.reject(ERROR_PATH_MISSING)
                return
            }
            val performanceMode: Int? =
                call.getInt("performanceMode", FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            val landmarkMode: Int? =
                call.getInt("landmarkMode", FaceDetectorOptions.LANDMARK_MODE_NONE)
            val contourMode: Int? =
                call.getInt("contourMode", FaceDetectorOptions.CONTOUR_MODE_NONE)
            val classificationMode: Int? =
                call.getInt("classificationMode", FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            val minFaceSize: Float? = call.getFloat("minFaceSize", 0.1f)
            val enableTracking: Boolean = call.getBoolean("enableTracking", false) == true
            val lensFacingOption: String = call.getString("lensFacing", "BACK")!!
            val lensFacing: Int =
                if (lensFacingOption == "FRONT") CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
            val image: InputImage? = implementation?.createInputImageFromFilePath(path)
            if (image == null) {
                call.reject(ERROR_LOAD_IMAGE_FAILED)
                return
            }
            val options = performanceMode?.let {
//            checking for null now. ill probably set a default config later
                if (landmarkMode != null && contourMode != null && classificationMode != null && minFaceSize != null) {
                    ProcessImageOptions(
                        it,
                        landmarkMode,
                        contourMode,
                        classificationMode,
                        minFaceSize,
                        enableTracking,
                        image,
                        lensFacing
                    )
                } else {
                    call.reject(ERROR_MISSING_PARAM)
                    return
                }
            }
            if (options != null) {
                implementation?.processImage(
                    options,
                    object : ScanImageResultsCallback {

                        override fun success(faces: List<Face>) {
                            call.resolve(ProcessImageResult().toJSObject(faces))

                        }
                        override fun cancel() {
                            call.reject(ERROR_PROCESS_IMAGE_CANCELED)
                        }
                        override fun error(exception: Exception?) {
                            val message = exception?.message
                            Logger.error(TAG, message, exception)
                            call.reject(message)
                        }
                    }
                )
            }
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(TAG, message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun stopScan(call: PluginCall) {
        try {
            activity
                .runOnUiThread {
                    implementation?.stopScan()
                    call.resolve()
                }
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun isSupported(call: PluginCall) {
        try {
            val result = JSObject()
            implementation?.let { result.put("supported", it.isSupported) }
            call.resolve(result)
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun enableTorch(call: PluginCall) {
        try {
            implementation?.enableTorch()
            call.resolve()
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun disableTorch(call: PluginCall) {
        try {
            implementation?.disableTorch()
            call.resolve()
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun toggleTorch(call: PluginCall) {
        try {
            implementation?.toggleTorch()
            call.resolve()
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun isTorchEnabled(call: PluginCall) {
        try {
            val result = JSObject()
            implementation?.let { result.put("enabled", it.isTorchEnabled) }
            call.resolve(result)
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun isTorchAvailable(call: PluginCall) {
        try {
            val result = JSObject()
            implementation?.let { result.put("available", it.isTorchAvailable) }
            call.resolve(result)
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    fun openSettings(call: PluginCall) {
        try {
            implementation?.openSettings(call)
        } catch (exception: Exception) {
            val message = exception.message
            Logger.error(message, exception)
            call.reject(message)
        }
    }

    @PluginMethod
    override fun requestPermissions(call: PluginCall) {
        // Check if the permission is already declared in manifest.
        if (isPermissionDeclared(CAMERA)) {
            // Prompt the user for permission.
            super.requestPermissions(call)
        } else {
            checkPermissions(call)
        }
    }

    public override fun requestPermissionForAlias(
        alias: String,
        call: PluginCall,
        callbackName: String
    ) {
        super.requestPermissionForAlias(alias, call, callbackName)
    }

    @ActivityCallback
    fun openSettingsResult(call: PluginCall?, result: ActivityResult?) {
        try {
            if (call == null) {
                Logger.debug("openSettingsResult was called with empty call parameter.")
                return
            }
            call.resolve()
        } catch (exception: Exception) {
            Logger.error(exception.message, exception)
        }
    }

    @PermissionCallback
    private fun cameraPermissionsCallback(call: PluginCall) {
        if (call.getMethodName() == "startActiveScan") {
            startActiveScan(call)
        }
    }

    fun notifyFaceScannedListener(face: Face?, imageSize: Point?) {
        try {
            Log.println(Log.INFO, TAG, "notifyFaceScannedListener:: $face")
            val screenSize: Point = screenSize
            // TODO: consider adding image size and screen size to the result
            val faceResult: JSObject? =
                face?.let { ProcessImageResult().createSingleFaceResult(it) } //, imageSize, screenSize)
            val result = JSObject()
            result.put("face", faceResult)
            Log.println(Log.INFO, TAG, "result being passed back:: $result")
            notifyListeners(FACE_SCANNED_EVENT, result)
        } catch (exception: Exception) {
            Logger.error(exception.message, exception)
        }
    }

    fun notifyScanErrorListener(message: String?) {
        try {
            val result = JSObject()
            result.put("message", message)
            notifyListeners(SCAN_ERROR_EVENT, result)
        } catch (exception: Exception) {
            Logger.error(exception.message, exception)
        }
    }


    private val screenSize: Point
        /**
         * Returns the display size without navigation bar height and status bar height.
         */
        private get() {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
        }

    companion object {
        const val TAG = "FaceDetectionPlugin"
        const val CAMERA = "camera"
        const val FACE_SCANNED_EVENT = "faceScanned"
        const val SCAN_ERROR_EVENT = "scanError"
        const val ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled."
        const val ERROR_PATH_MISSING = "path must be provided."
        const val ERROR_LOAD_IMAGE_FAILED = "image could not be loaded."
        const val ERROR_MISSING_PARAM = "Missing one or more parameters"
        const val ERROR_PERMISSION_DENIED = "User denied access to camera."
    }
}
