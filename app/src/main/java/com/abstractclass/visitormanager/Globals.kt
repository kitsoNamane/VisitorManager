package com.abstractclass.visitormanager

import android.Manifest

class Globals {
    companion object {

        // This is an arbitrary number we are using to keep track of the permission
        // request. Where an app has multiple context for requesting permission,
        // this can help differentiate the different contexts.
        const val REQUEST_CODE_PERMISSIONS = 10

        // This is an array of all the permission specified in the manifest.
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}