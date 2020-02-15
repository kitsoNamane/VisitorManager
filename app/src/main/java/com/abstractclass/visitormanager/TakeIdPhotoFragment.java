package com.abstractclass.visitormanager;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.Preview;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abstractclass.visitormanager.models.Person;
import com.abstractclass.visitormanager.models.Visitor;
import com.abstractclass.visitormanager.view_models.VisitorViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TakeIdPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeIdPhotoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    VisitorViewModel visitorViewModel;
    Visitor visitor;
    TextureView textureView;
    Preview preview;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private Executor executor = Executors.newSingleThreadExecutor();


    public TakeIdPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TakeIdPhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TakeIdPhotoFragment newInstance(String param1, String param2) {
        TakeIdPhotoFragment fragment = new TakeIdPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        visitorViewModel = new ViewModelProvider(requireActivity()).get(VisitorViewModel.class);
        visitorViewModel.init(this.getContext());

        /** We can observe the ViewModel for changes, for example:
         *  We can send emails notifications after every 2 visitors entry
         */
        visitorViewModel.getVisitors().observe(getViewLifecycleOwner(), visitors -> {
           if(visitors.size() % 2 == 0) {
               Toast.makeText(this.getContext(),
                       "2 more visors added, total visitors : "+visitors.size(),
                       Toast.LENGTH_LONG).show();
           }
        });

        /** add a visitor using the ViewModel
         *  we first initialize our data classes
         */

        visitor = new Visitor();
        Person person = new Person();
        person.setFirstName("kitso");
        person.setLastName("Namane");
        person.setSex("male");

        // add person to visitor
        visitor.setPerson(person);
        visitor.setPurpose("Code Exploration");

        // add visitor to database
        visitorViewModel.addVisitor(visitor);


        person.setFirstName("Olebogeng");
        person.setLastName("Mbedzi");
        person.setSex("male");

        visitor.setPerson(person);
        visitor.setPurpose("Business Dealings");
        visitorViewModel.addVisitor(visitor);

        // NB: you have to add a visitor to the database before we can resuse the same visitor container
        // to add a second visitor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_take_id_photo, container, false);
        textureView = view.findViewById(R.id.view_finder);
        if(allPermissionGranted()){
            textureView.post(()->startCamera());
        }
        else{
            ActivityCompat.requestPermissions(this.getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updateTransform();
            }
        });
        //TextureView textureView = view.findViewById(R.id.camera_view);
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                textureView.post(()->startCamera());
            } else{
                Toast.makeText(this.getContext(), "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean allPermissionGranted() {

        for(String permission : REQUIRED_PERMISSIONS){

            if(ContextCompat.checkSelfPermission(this.getContext(), permission) != PackageManager.PERMISSION_GRANTED){

                return false;
            }
        }
        return true;
    }

    private void startCamera() {
        /**
        Rational aspectRatio = new Rational(textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());

        Preview preview = new Preview.Builder().setTargetResolution(screen).build();
        preview.setSurfaceProvider(preview.getSurfaceProvider());
         */

    }

    private void updateTransform() {


    }
}
