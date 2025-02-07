package com.eduard05.objectDetector.ui;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.eduard05.objectDetector.R;
import com.eduard05.objectDetector.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainFragment extends Fragment {

    private final String[]  PERMISSIONS =  {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
    };

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button btnCamera = view.findViewById(R.id.btn_to_camera);
        btnCamera.setOnClickListener(v -> redirectToCamera());

        Util.requestPermission(PERMISSIONS, this);

        return view;
    }

    private void redirectToCamera(){
        Log.d("TAKE PICTURE", "BUTTON `TAKE PICTURE` HAS CLICKED");
        NavHostFragment.findNavController(MainFragment.this).navigate(R.id.detect_camera_fragment);
    }
}
