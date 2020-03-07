package com.abstractclass.visitormanager.views.fragments

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abstractclass.visitormanager.Globals
import com.abstractclass.visitormanager.R
import com.abstractclass.visitormanager.models.Person
import com.abstractclass.visitormanager.text_recognition.MRZImangeAnalyzer
import com.abstractclass.visitormanager.utils.Utils
import com.abstractclass.visitormanager.view_models.MRZViewModel
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var mrzViewModel: MRZViewModel? = null
    private var mrzDecoder: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            viewFinder.post {
                Log.d("ScanID", "Back Button Pressed")
                CameraX.unbindAll()
                Navigation.findNavController(getView()!!).popBackStack()
            }
        }
    }

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    private fun startCamera() {
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            // In our analysis, we care more about the latest image than
            // analyzing *every* image
            setImageReaderMode(
                    ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()

        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            setAnalyzer(executor, MRZImangeAnalyzer(mrzViewModel))
        }

        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        CameraX.bindToLifecycle(viewLifecycleOwner, preview, analyzerUseCase)
    }

    private fun updateTransform() {
        // TODO: Implement camera viewfinder transformations
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }
    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Globals.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this.context,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = Globals.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mrzViewModel = ViewModelProvider(requireActivity()).get(MRZViewModel::class.java)

        //activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        mrzViewModel?.getPerson()?.observe(viewLifecycleOwner, Observer<Person?> { person ->
            if(person != null) {
                Toast.makeText(context, person.toString(), Toast.LENGTH_LONG).show()
                //val actionPerson = PhotoIdFragmentDirections.actionPhotoIdFragmentToIdDecodeInfoFragment(person)
                val actionPerson = SignInFragmentDirections.actionPhotoId(person)
                viewFinder.post {
                    mrzViewModel?.setTextblock("reset person ${Utils.getCurrentTime().toString()}")
                    CameraX.unbindAll()
                    Log.d("AppData", "back button override")
                    Navigation.findNavController(getView()!!).navigate(actionPerson)
                }
            }
        })


        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                    this.requireActivity(), Globals.REQUIRED_PERMISSIONS, Globals.REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_sign_in, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Abstract Class"
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = "Sign In"
        viewFinder = view.findViewById(R.id.view_finder)
        mrzDecoder = view.findViewById(R.id.decode_mrz)
        /**
        val toolbar : androidx.appcompat.widget.Toolbar?  = activity?.findViewById(R.id.toolbar)
        toolbar?.setNavigationOnClickListener {
            viewFinder.post {
                CameraX.unbindAll()
                Log.d("AppData", "back button override")
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).popBackStack()
            }
        }
        */
        return view
    }

    override public fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                viewFinder.post{
                    Log.d("AppData", "Can we do this please")
                    CameraX.unbindAll()
                    Navigation.findNavController(getView()!!).popBackStack()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
