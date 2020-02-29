package com.abstractclass.visitormanager.text_recognition

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.abstractclass.visitormanager.view_models.MRZViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata

class MRZImangeAnalyzer(mrzViewModel: MRZViewModel?) : ImageAnalysis.Analyzer {
    private val mrzViewModel: MRZViewModel? = mrzViewModel
    private val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy?, rotationDegrees: Int) {
        val mediaImage = imageProxy?.image
        val imageRotation = degreesToFirebaseRotation(rotationDegrees)
        if (mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            // Pass image to an ML Kit Vision API
            val result = detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    for (block in firebaseVisionText.textBlocks) {
                        val boundingBox = block.boundingBox
                        val cornerPoints = block.cornerPoints
                        boundingBox?.set(1, 1, 1,1)
                        mrzViewModel?.setTextblock(block.text)
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }

    }

}