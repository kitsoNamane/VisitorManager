package com.abstractclass.visitormanager.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abstractclass.visitormanager.R
import com.abstractclass.visitormanager.reports.ExcelWorkSheet
import com.abstractclass.visitormanager.utils.Email
import com.abstractclass.visitormanager.utils.Utils
import com.abstractclass.visitormanager.view_models.VisitorViewModel

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
        if(Utils.isExternalStorageReadable()) {
            Toast.makeText(context, "external unreadable", Toast.LENGTH_LONG).show()
        }
        if(Utils.isExternalStorageWritable()) {
            Toast.makeText(context, "external unwritable", Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        visitorViewModel = ViewModelProvider(this).get(VisitorViewModel::class.java)
        visitorViewModel?.initialize(context!!)

        email = Email()
        excelWorkSheet = ExcelWorkSheet(context)

        excelWorkSheet?.addVisitors(visitorViewModel!!.getVisitors()!!.value)

        email?.addresses = listOf("kitso.namane@studentmail.biust.ac.bw").toTypedArray()
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
