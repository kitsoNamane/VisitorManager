package com.abstractclass.visitormanager.text_recognition

import android.content.Context
import android.net.Uri
import com.abstractclass.visitormanager.models.Person
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.io.IOException

class TextRecognition(private val context: Context?, private val image_uri: Uri?) {
    private val text: String? = null
    private var firebaseVisionImage: FirebaseVisionImage? = null
    private val person: Person? = null
    private val detector: FirebaseVisionTextRecognizer?
    private fun createFirebaseVisionImage(): Boolean {
        try {
            firebaseVisionImage = FirebaseVisionImage.fromFilePath(context!!, image_uri!!)
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun recognizeText(): Boolean {
        createFirebaseVisionImage()
        val result = detector?.processImage(firebaseVisionImage!!)
                ?.addOnSuccessListener { firebaseVisionText ->
                    for (block in firebaseVisionText.textBlocks) {
                        val blockText = block.text
                        /**
                         * if(MRTD.isValidTD1(blockText)) {
                         * Log.d("Block text:", blockText);
                         * MRTD mrtd = new MRTD(blockText);
                         * person = mrtd.getPerson();
                         * Log.d("Person Id", person.getNationalId());
                         * text = blockText;
                         * return;
                         * } else {
                         * Log.d("Block test", "Invalid : "+blockText);
                         * text = null;
                         * }
                         * Log.d("Block text", "Completed Analyzing image");
                         */
                        /**
                         * if(MRTD.isValidTD1(blockText)) {
                         * Log.d("Block text:", blockText);
                         * MRTD mrtd = new MRTD(blockText);
                         * person = mrtd.getPerson();
                         * Log.d("Person Id", person.getNationalId());
                         * text = blockText;
                         * return;
                         * } else {
                         * Log.d("Block test", "Invalid : "+blockText);
                         * text = null;
                         * }
                         * Log.d("Block text", "Completed Analyzing image");
                         */
                    }
                }
                ?.addOnFailureListener { e ->
                    // Task failed with an exception
// ...
                    e.printStackTrace()
                }
        return text != null
    }

    fun getText(): String? {
        return text
    }

    fun getPerson(): Person? {
        return person
    }

    init {
        detector = FirebaseVision.getInstance().onDeviceTextRecognizer
    }
}