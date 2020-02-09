package com.abstractclass.visitormanager.google;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;

public class TextRecognition
{

    private String text;
    private FirebaseVisionImage firebaseVisionImage;
    private Context context;
    private Uri image_uri;


    public TextRecognition(Context context, Uri image_uri)
    {
        this.context = context;
        this.image_uri = image_uri;
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
}
