package com.abstractclass.visitormanager.utils

import android.content.Intent
import android.net.Uri

class Email {
    var email: String? = null
    get() = field
    set(value) {
        field = value
    }

    var addresses: Array<String>? = null
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
        emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent?.setType("plain/text")
        emailIntent?.putExtra(Intent.EXTRA_EMAIL, email)
        emailIntent?.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent?.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent?.putExtra(Intent.EXTRA_STREAM, file);
        return emailIntent
    }

    fun composeEmail() : Intent {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            //data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message);
            putExtra(Intent.EXTRA_STREAM, file);
        }
        return intent
    }
}