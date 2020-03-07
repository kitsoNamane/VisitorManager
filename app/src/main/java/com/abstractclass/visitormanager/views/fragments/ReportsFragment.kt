package com.abstractclass.visitormanager.views.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abstractclass.visitormanager.Globals
import com.abstractclass.visitormanager.R
import com.abstractclass.visitormanager.reports.ExcelWorkSheet
import com.abstractclass.visitormanager.utils.Email
import com.abstractclass.visitormanager.view_models.VisitorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [ReportsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var excelWorkSheet: ExcelWorkSheet? = null
    private var email: Email? = null
    private var visitorViewModel: VisitorViewModel? = null
    private var sendEmailBtn: MaterialButton? = null
    private var emailTextInput: TextInputEditText? = null
    private var isEmailEntered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)
        sendEmailBtn = view.findViewById(R.id.send_email)
        emailTextInput = view.findViewById(R.id.email_text)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Abstract Class"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "Reports"
        emailTextInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if(s.length > 0) {
                    enableButton(true)
                    email?.addresses = arrayOf(s.toString())
                } else {
                    enableButton(false)
                }
            }
        })
        return view
    }

    private fun enableButton(enabled: Boolean) {
        sendEmailBtn?.setOnClickListener(View.OnClickListener {
            if(enabled)  {
                sendEmail()
            } else {
                Snackbar.make(getView()!!, "Please enter an email address", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = Globals.FILE_READ_WRITE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Globals.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
            } else {
                Toast.makeText(this.context,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        visitorViewModel = ViewModelProvider(this).get(VisitorViewModel::class.java)
        visitorViewModel?.initialize(context!!)
        email = Email()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                    this.requireActivity(), Globals.FILE_READ_WRITE_PERMISSIONS, Globals.REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun sendEmail() {
        excelWorkSheet = ExcelWorkSheet(context)
        excelWorkSheet?.addVisitors(visitorViewModel!!.getVisitors()!!.value)
        visitorViewModel!!.getVisitors()!!.value?.forEach { visitor->
           Log.d("AppData", visitor.toString())
        }
        email?.addresses = arrayOf(emailTextInput?.text.toString())
        //listOf("kitso.namane@studentmail.biust.ac.bw").toTypedArray()
        email?.file = excelWorkSheet?.getFile()
        email?.message = "Here are all your visitors"
        email?.subject = "Today's Visitor Log Registry"
        val emailIntent = email?.composeEmail()
        if (emailIntent?.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReportsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ReportsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
