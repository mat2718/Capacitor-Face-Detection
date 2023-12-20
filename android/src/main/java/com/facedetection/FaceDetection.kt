package com.facedetection

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.media.Image
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.Display
import android.view.WindowManager
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.facedetection.callbacks.ScanImageResultsCallback
import com.facedetection.callbacks.StartScanResultCallback
import com.facedetection.helpers.ProcessImageOptions
import com.facedetection.helpers.ProcessImageResult
import com.getcapacitor.PermissionState
import com.getcapacitor.PluginCall
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetection(private val plugin: FaceDetectionPlugin) : ImageAnalysis.Analyzer {
    private val displaySize: Point
    private var faceDetectorInstance: FaceDetector? = null
    private var camera: Camera? = null
    private var scanSettings: ProcessImageOptions? = null
    private var processCameraProvider: ProcessCameraProvider? = null

    var isTorchEnabled = false
        private set

    init {
        displaySize = getDisplaySize()
    }

    /**
     * Must run on UI thread.
     */
    fun startScan(options: ProcessImageOptions, callback: StartScanResultCallback) {
        // Stop the camera if running
        stopScan()
        // Hide WebView background
        hideWebViewBackground()
        val performanceMode: Int = options.performanceMode
        val landmarkMode: Int = options.landmarkMode
        val contourMode: Int = options.contourMode
        val classificationMode: Int = options.classificationMode
        val minFaceSize: Float = options.minFaceSize
        val enableTracking: Boolean = options.isTrackingEnabled
        val builder: FaceDetectorOptions.Builder = FaceDetectorOptions.Builder()
        builder.setPerformanceMode(performanceMode)
        builder.setLandmarkMode(landmarkMode)
        builder.setContourMode(contourMode)
        builder.setClassificationMode(classificationMode)
        builder.setMinFaceSize(minFaceSize)
        if (enableTracking) {
            builder.enableTracking()
        }
        val faceDetectorOptions: FaceDetectorOptions = builder.build()
        faceDetectorInstance =
            com.google.mlkit.vision.face.FaceDetection.getClient(faceDetectorOptions)


        val imageAnalysis: ImageAnalysis =
            ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(plugin.context), this)
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(
                plugin.context
            )
        cameraProviderFuture.addListener(
            Runnable {
                try {
                    processCameraProvider = cameraProviderFuture.get()
                    val cameraSelector: CameraSelector? =
                        options.lensFacing?.let {
                            CameraSelector.Builder().requireLensFacing(it)
                                .build()
                        }
                    val previewView: PreviewView = plugin.activity.findViewById<PreviewView>(R.id.preview_view)
                    previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
                    val preview: Preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    // Start the camera
                    camera = cameraSelector?.let {
                        // i hate !!...
                        processCameraProvider!!.bindToLifecycle(
                            plugin.context as LifecycleOwner,
                            it,
                            preview,
                            imageAnalysis
                        )
                    }
                    callback.success()
                } catch (exception: Exception) {
                    callback.error(exception)
                }
            },
            ContextCompat.getMainExecutor(plugin.context)
        )
    }
    fun createInputImageFromFilePath(path: String): InputImage? {
        return try {
            InputImage.fromFilePath(plugin.context, Uri.parse(path))
        } catch (exception: Exception) {
            null
        }
    }

    val cameraPermission: PermissionState
        get() = plugin.getPermissionState(FaceDetectionPlugin.CAMERA)

    fun requestCameraPermission(call: PluginCall?) {
        if (call != null) {
            plugin.requestPermissionForAlias(
                FaceDetectionPlugin.CAMERA,
                call,
                "cameraPermissionsCallback"
            )
        }
    }

    fun openSettings(call: PluginCall?) {
        val uri: Uri = Uri.fromParts("package", plugin.appId, null)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri)
        plugin.startActivityForResult(call, intent, "openSettingsResult")
    }

    fun requestCameraPermissionIfNotDetermined(call: PluginCall?): Boolean {
        val state: PermissionState = cameraPermission
        return if (state == PermissionState.GRANTED) {
            true
        } else if (state == PermissionState.DENIED) {
            throw Exception(FaceDetectionPlugin.ERROR_PERMISSION_DENIED)
        } else {
            requestCameraPermission(call)
            false
        }
    }

    fun processImage(options: ProcessImageOptions, callback: ScanImageResultsCallback) {
        val inputImage: InputImage? = options.getInputImage()
        val performanceMode: Int = options.performanceMode
        val landmarkMode: Int = options.landmarkMode
        val contourMode: Int = options.contourMode
        val classificationMode: Int = options.classificationMode
        val minFaceSize: Float = options.minFaceSize
        val enableTracking: Boolean = options.isTrackingEnabled
        val builder: FaceDetectorOptions.Builder = FaceDetectorOptions.Builder()
        builder.setPerformanceMode(performanceMode)
        builder.setLandmarkMode(landmarkMode)
        builder.setContourMode(contourMode)
        builder.setClassificationMode(classificationMode)
        builder.setMinFaceSize(minFaceSize)
        if (enableTracking) {
            builder.enableTracking()
        }
        val faceDetectorOptions: FaceDetectorOptions = builder.build()
        val faceDetector: FaceDetector =
            com.google.mlkit.vision.face.FaceDetection.getClient(faceDetectorOptions)
        plugin
            .activity
            .runOnUiThread {
                if (inputImage != null) {
                    faceDetector
                        .process(inputImage)
                        .addOnSuccessListener { faceResults ->
                            faceDetector.close()
                            val result = ProcessImageResult().toJSObject(faceResults)
                            Log.println(Log.INFO, "FaceDetection", result.toString())
                            callback.success(faceResults)
                        }
                        .addOnCanceledListener {
                            faceDetector.close()
                            callback.cancel()
                        }
                        .addOnFailureListener { exception ->
                            faceDetector.close()
                            callback.error(exception)
                        }
                }
            }
    }

    /**
     * Must run on UI thread.
     */
    fun stopScan() {
        showWebViewBackground()
        disableTorch()
        // Stop the camera
        processCameraProvider?.unbindAll()
        camera = null
        faceDetectorInstance = null
        scanSettings = null
    }

    private fun getDisplaySize(): Point {
        val wm: WindowManager =
            plugin.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.getDefaultDisplay()
        val size = Point()
        display.getRealSize(size)
        return size
    }

    /**
     * Must run on UI thread.
     */
    private fun hideWebViewBackground() {
        plugin.bridge.webView.setBackgroundColor(Color.TRANSPARENT)
    }

    /**
     * Must run on UI thread.
     */
    private fun showWebViewBackground() {
        plugin.bridge.webView.setBackgroundColor(Color.WHITE)
    }

    val isSupported: Boolean
        get() = plugin.context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

    fun enableTorch() {
        if (camera == null) {
            return
        }
        camera!!.cameraControl.enableTorch(true)
        isTorchEnabled = true
    }

    fun disableTorch() {
        if (camera == null) {
            return
        }
        camera!!.cameraControl.enableTorch(false)
        isTorchEnabled = false
    }

    fun toggleTorch() {
        if (isTorchEnabled) {
            disableTorch()
        } else {
            enableTorch()
        }
    }

    val isTorchAvailable: Boolean
        get() = plugin.context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    
    
    @androidx.camera.core.ExperimentalGetImage
    override fun  analyze(imageProxy: ImageProxy) {
        val image: Image? = imageProxy.image
        if (image == null || faceDetectorInstance == null) {
            return
        }
        val inputImage: InputImage =
            InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
        val imageSize = Point(inputImage.width, inputImage.height)
        faceDetectorInstance!!
            .process(inputImage)
            .addOnSuccessListener { faces ->
                Log.println(Log.INFO, TAG, faces.toString())
                if (scanSettings == null) {
                    // Scanning stopped while processing the image
                    return@addOnSuccessListener
                }
                for (face in faces) {
                    handleScannedface(face, imageSize)
                }
            }
            .addOnFailureListener { exception -> handleScanError(exception) }
            .addOnCompleteListener {
                imageProxy.close()
                image.close()
            }
    }

    private fun handleScannedface(face: Face, imageSize: Point) {
        plugin.notifyFaceScannedListener(face, imageSize)
    }

    private fun handleScanError(exception: Exception) {
        plugin.notifyScanErrorListener(exception.message)
    }

    companion object {
        const val TAG = "FaceDetection"
    }
}
