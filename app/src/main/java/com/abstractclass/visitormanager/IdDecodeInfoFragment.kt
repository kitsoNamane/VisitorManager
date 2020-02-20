package com.abstractclass.visitormanager

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abstractclass.visitormanager.models.Person
import com.abstractclass.visitormanager.models.Visitor
import com.abstractclass.visitormanager.view_models.VisitorViewModel
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
    private var imagePath: String? = null
    private var imageUri: Uri? = null
    private var visitorViewModel: VisitorViewModel? = null
    private var imageScanResult: ImageView? = null
    private var person: Person? = null
    private var visitor: Visitor? = null

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
        //visitorViewModel = ViewModelProvider(this).get(VisitorViewModel::class.java)
        //visitorViewModel?.init(context)
        val view =  inflater.inflate(R.layout.fragment_id_decode_info, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Abstract Class"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "ID Verification"
        imageScanResult = view.findViewById(R.id.scan_results)
        //Toast.makeText(requireContext(), imagePath, Toast.LENGTH_LONG).show()
        //Glide.with(this).load(imageUri).into(view.findViewById(R.id.scan_results))

        view.findViewById<MaterialTextView>(R.id.name).setText(
                String.format("%s %s %s", person?.firstName, person?.middleName, person?.lastName)
        )

        view.findViewById<MaterialTextView>(R.id.id_number).setText(
                person?.nationalId
        )
        view.findViewById<MaterialTextView>(R.id.birthdate).setText(
                person?.birthDate.toString()
        )
        view.findViewById<MaterialTextView>(R.id.gender).setText(
                person?.sex
        )
        view.findViewById<MaterialTextView>(R.id.nationality).setText(
                "Motswana"
        )
        // T save to DataBase, create Visitor object, add person object to it and save to
        // database.
        visitor?.person = person
        //visitorViewModel?.addVisitor(visitor)


        return view;
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
