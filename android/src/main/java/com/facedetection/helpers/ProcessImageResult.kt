package com.facedetection.helpers

import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark
import android.graphics.Rect
import android.graphics.PointF

// this handles the face scan results and bundles it in a nice bow :)
class ProcessImageResult {

    fun toJSObject(faces: List<Face>): JSObject {
        val facesResult: JSArray = createMultiFacesResults(faces)
        val result = JSObject()
        result.put("faces", facesResult)
        return result
    }

    private fun createMultiFacesResults(faces: List<Face>): JSArray {
        val result = JSArray()
        for (face in faces) {
            val faceResult: JSObject = createSingleFaceResult(face)
            result.put(faceResult)
        }
        return result
    }

    fun createSingleFaceResult(face: Face): JSObject {
        val result = JSObject()
        val boundingBox: Rect = face.boundingBox
        val boundsResult: JSObject = createBoundsResult(boundingBox)
        result.put("bounds", boundsResult)
        val landmarksResult = JSArray()
        for (landmark in face.allLandmarks) {
            val landmarkResult: JSObject = createLandmarkResult(landmark)
            landmarksResult.put(landmarkResult)
        }
        if (landmarksResult.length() > 0) {
            result.put("landmarks", landmarksResult)
        }
        val contoursResult = JSArray()
        for (contour in face.allContours) {
            val points: List<PointF> = contour.points
            val pointsResult = JSArray()
            for (point in points) {
                val positionResult: JSObject = createPositionResult(point)
                pointsResult.put(positionResult)
            }
            if (pointsResult.length() > 0) {
                val contourResult: JSObject = createContourResult(contour, pointsResult)
                contoursResult.put(contourResult)
            }
        }
        if (contoursResult.length() > 0) {
            result.put("contours", contoursResult)
        }
        val trackingId: Int? = face.trackingId
        if (trackingId != null) {
            result.put("trackingId", trackingId)
        }
        result.put("headEulerAngleX", face.headEulerAngleX)
        result.put("headEulerAngleY", face.headEulerAngleY)
        result.put("headEulerAngleZ", face.headEulerAngleZ)
        val smilingProbability: Float? = face.smilingProbability
        if (smilingProbability != null) {
            result.put("smilingProbability", smilingProbability)
        }
        val leftEyeOpenProbability: Float? = face.leftEyeOpenProbability
        if (leftEyeOpenProbability != null) {
            result.put("leftEyeOpenProbability", leftEyeOpenProbability)
        }
        val rightEyeOpenProbability: Float? = face.rightEyeOpenProbability
        if (rightEyeOpenProbability != null) {
            result.put("rightEyeOpenProbability", rightEyeOpenProbability)
        }
        return result
    }

    private fun createBoundsResult(boundingBox: Rect): JSObject {
        val result = JSObject()
        result.put("left", boundingBox.left)
        result.put("top", boundingBox.top)
        result.put("right", boundingBox.right)
        result.put("bottom", boundingBox.bottom)
        return result
    }

    private fun createLandmarkResult(landmark: FaceLandmark): JSObject {
        val positionResult: JSObject = createPositionResult(landmark.position)
        val result = JSObject()
        result.put("type", landmark.landmarkType)
        result.put("position", positionResult)
        return result
    }

    private fun createPositionResult(point: PointF): JSObject {
        val result = JSObject()
        result.put("x", point.x.toDouble())
        result.put("y", point.y.toDouble())
        return result
    }

    private fun createContourResult(contour: FaceContour, pointsResult: JSArray): JSObject {
        val result = JSObject()
        result.put("type", contour.faceContourType)
        result.put("points", pointsResult)
        return result
    }
}
