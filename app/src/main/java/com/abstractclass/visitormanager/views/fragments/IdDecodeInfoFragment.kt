package com.abstractclass.visitormanager.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abstractclass.visitormanager.MainActivity
import com.abstractclass.visitormanager.R
import com.abstractclass.visitormanager.models.Person
import com.abstractclass.visitormanager.models.Visitor
import com.abstractclass.visitormanager.utils.Utils
import com.abstractclass.visitormanager.view_models.VisitorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [IdDecodeInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IdDecodeInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var visitorViewModel: VisitorViewModel? = null
    private var imageScanResult: ImageView? = null
    private var person: Person? = null
    private var visitor: Visitor? = null
    private var phoneNumber: TextInputEditText? = null
    private var purposeOfVisit: TextInputEditText? = null
    private var plateNumber: TextInputEditText? = null
    private var addVisitor: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        person = arguments?.let { IdDecodeInfoFragmentArgs.fromBundle(it).person }
        //imagePath = arguments?.let { IdDecodeInfoFragmentArgs.fromBundle(it).imagePath }
        //imageUri = Uri.fromFile(File(imagePath))
        visitor = Visitor()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_id_decode_info, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Abstract Class"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "ID Verification"

        imageScanResult = view.findViewById(R.id.scan_results)
        view.findViewById<MaterialTextView>(R.id.name).setText(
                String.format("%s %s %s", person?.firstName, person?.middleName, person?.lastName)
        )

        view.findViewById<MaterialTextView>(R.id.id_number).setText(
                person?.nationalId
        )
        view.findViewById<MaterialTextView>(R.id.birthdate).setText(
            Utils.timeStampToString(
                person?.birthDate!!.toLong()
            ).toString()
        )
        view.findViewById<MaterialTextView>(R.id.gender).setText(
                person?.sex
        )
        view.findViewById<MaterialTextView>(R.id.nationality).setText(
                "Motswana"
        )

        phoneNumber = view.findViewById(R.id.phone_number_text)
        purposeOfVisit = view.findViewById(R.id.purpose_text)
        plateNumber = view.findViewById(R.id.plate_number_text)
        addVisitor = view.findViewById(R.id.add_btn)


        // T save to DataBase, create Visitor object, add person object to it and save to
        // database.
        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        visitorViewModel = ViewModelProvider(this).get(VisitorViewModel::class.java)
        visitorViewModel?.initialize(context!!)

        addVisitor?.setOnClickListener(View.OnClickListener {
            visitor?.person = person
            visitor?.timeIn = Utils.getCurrentTime()
            visitorViewModel?.addVisitor(visitor)
            Toast.makeText(context, "Added Visitor", Toast.LENGTH_LONG).show()
            /**
            if(visitorViewModel?.getVisitor(person?.nationalId!!) == null) {
                Toast.makeText(context, "Added Visitor", Toast.LENGTH_LONG).show()
            } else if(visitorViewModel?.getVisitor(person?.nationalId!!)?.timeOut == null) {
                visitorViewModel?.addVisitor(visitor)
                Toast.makeText(context, visitorViewModel?.getVisitor(person?.nationalId!!).toString(), Toast.LENGTH_LONG).show()
            }
            */
            MainActivity.navController?.navigate(ReportsFragmentDirections.actionReports())
        })

        phoneNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 0) {
                    visitor?.phone = s.toString()
                    MainActivity.navController?.navigate(ReportsFragmentDirections.actionReports())
                }
            }
        })

        purposeOfVisit?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 0) {
                    visitor?.purpose = s.toString()
                }
            }
        })

        plateNumber?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 0) {
                    visitor?.plateNumber = s.toString()
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IdDecodeInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                IdDecodeInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
