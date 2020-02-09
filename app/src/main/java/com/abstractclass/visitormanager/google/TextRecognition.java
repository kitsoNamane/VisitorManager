package com.abstractclass.visitormanager.google;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.abstractclass.visitormanager.models.Person;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;

public class TextRecognition
{

    private String text;
    private FirebaseVisionImage firebaseVisionImage;
    private Context context;
    private Uri image_uri;
    private FirebaseVisionTextRecognizer detector;


    public TextRecognition(Context context, Uri image_uri)
    {
        this.context = context;
        this.image_uri = image_uri;
        this.detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
    }

    private boolean createFirebaseVisionImage()
    {
        try
        {
            this.firebaseVisionImage = FirebaseVisionImage.fromFilePath(this.context, this.image_uri);
        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Task<FirebaseVisionText> getVisionText()
    {
        this.createFirebaseVisionImage();
        return  detector.processImage(this.firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        // Task completed successfully
                        // ...
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                e.printStackTrace();
                            }
                        });



    }

    public Person getPersonData()
    {
        Person person = null;
        Task<FirebaseVisionText> result = this.getVisionText();

        String visionTextTask = result.getResult().getText();

        for (FirebaseVisionText.TextBlock block: result.getResult().getTextBlocks())
        {
            String blockText = block.getText();
        }

        return person;
    }


}
