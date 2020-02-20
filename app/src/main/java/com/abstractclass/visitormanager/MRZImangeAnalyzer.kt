package com.abstractclass.visitormanager

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.abstractclass.visitormanager.controller.MRTD
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata

public class MRZImangeAnalyzer : ImageAnalysis.Analyzer {

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
                            if(MRTD.isValidTD1(block.text)) {
                                Log.d("Block test Success", block.text)
                            } else {
                                Log.d("Block test Failed", block.text)
                            }

                            /**
                            for(text in block.text) {
                                if(MRTD.isValidTD1(text.toString())) {
                                   Log.d("Block test Success", text.toString())
                                } else {
                                    Log.d("Block test Failer", text.toString())
                                }
                            }
                            */
                        }
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }


        }

    }

}