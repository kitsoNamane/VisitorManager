package com.abstractclass.visitormanager.utils

import android.content.Intent
import android.net.Uri

class Email {
    var email: String? = null
    get() = field
    set(value) {
        field = value
    }

    var subject: String? = null
    get() = field
    set(value) {
        field = value
    }

    var message: String? = null
    get() = field
    set(value) {
        field = value
    }

    var file: Uri? = null
    get() = field
    set(value) {
        field = value
    }

    var emailIntent: Intent? = null

    fun initEmail(): Intent? {
        emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent?.setType("plain/text")
        emailIntent?.putExtra(Intent.EXTRA_EMAIL, email)
        emailIntent?.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent?.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent?.putExtra(Intent.EXTRA_STREAM, file);
        return emailIntent
    }
}