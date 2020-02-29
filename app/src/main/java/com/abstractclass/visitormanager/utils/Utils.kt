package com.abstractclass.visitormanager.utils

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        fun timeStampToString(timestamp : Long) : String  {
            val date = Date(timestamp)
            val format = "dd-MM-yyyy"
            val dateTimeFormat = SimpleDateFormat(format)
            return dateTimeFormat.format(date)
        }
    }
}