package com.abstractclass.visitormanager.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class Utils {
    companion object {

        fun timeStampToString(timestamp : Long) : String  {
            val date = Date(timestamp)
            val format = "dd-MM-yyyy"
            val dateTimeFormat = SimpleDateFormat(format)
            return dateTimeFormat.format(date)
        }

        fun getCurrentTime() : Long {
            val currentTimeStamp = TimeUnit.MICROSECONDS.toSeconds(System.currentTimeMillis())
            return currentTimeStamp
        }

        fun getISODate() : String {
            val c = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = dateFormat.format(c.time)
            return today
        }

        // Checks if a volume containing external storage is available
        // for read and write.
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        // Checks if a volume containing external storage is available to at least read.
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }

        fun createFile(context: Context, filename: String) : File {
            val rootDir = File(context.filesDir, "visitors_logs")
            if (!rootDir.exists()) {
                rootDir.mkdir()
            }
            val file = File(rootDir, filename)
            return file
        }

        fun refreshFragment(activity: FragmentActivity, fragment: Fragment) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .commit()
        }

        fun showKeyboard(view: View) {
            val imm: InputMethodManager = view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view.requestFocus()) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
        }

        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view: View? = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }
    }
}