package com.abstractclass.visitormanager;

import android.Manifest;

public class Globals {

    // This is an arbitrary number we are using to keep track of the permission
    // request. Where an app has multiple context for requesting permission,
    // this can help differentiate the different contexts.
    public static final int REQUEST_CODE_PERMISSIONS = 10;


    // This is an array of all the permission specified in the manifest.
    public static String REQUIRED_PERMISSIONS = Manifest.permission.CAMERA;
}
