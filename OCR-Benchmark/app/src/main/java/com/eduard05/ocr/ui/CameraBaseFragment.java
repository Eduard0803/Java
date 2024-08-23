package com.eduard05.ocr.ui;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.eduard05.ocr.R;
import com.eduard05.ocr.util.Util;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public abstract class CameraBaseFragment extends Fragment {

    private final String[]  PERMISSIONS =  {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    private ProcessCameraProvider cameraProvider;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;

    private boolean isRecording;

    protected ImageButton btnCapture;
    protected ImageButton btnStop;


    public CameraBaseFragment(){}

    @Override
    public void onStart(){
        super.onStart();
        cameraProviderFuture.addListener(() -> {
            try{
                this.cameraProvider = cameraProviderFuture.get();
                cameraStart();
            }catch(Exception e){
                Log.d("ERROR ON START CAMERA", "" + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);

        this.btnCapture = cameraView.findViewById(R.id.btn_camera_capture);
        this.btnCapture.setOnClickListener(v -> capturePhoto());

        this.btnStop = cameraView.findViewById(R.id.btn_stop_scan);
        this.btnStop.setOnClickListener(v -> stopCapture());

        this.previewView = cameraView.findViewById(R.id.previewView);
        this.cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        Util.requestPermission(PERMISSIONS, this);

        return cameraView;
    }

    private void initCapture(){
        this.isRecording = true;
        
        while(this.isRecording)
            capturePhoto();
    }

    private void cameraStart() {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector;

        cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();


        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 729))
                .setImageQueueDepth(15)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);

        Log.d("CameraStart", "Camera started with selector: " + cameraSelector.toString());
    }

    private void stopCapture(){
        this.isRecording = false;
    }


    private void capturePhoto(){
        long timestamp = Util.getTime();
        String namePicture = timestamp + "";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, namePicture);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/OCR Benchmark Fotos");
        }

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder( getActivity().getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues ).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Log.d("outputFileResults", outputFileResults.getSavedUri().getPath());
                successfulTakePicture(namePicture, timestamp, new File(Environment.getExternalStorageDirectory() + "/Pictures/OCR Benchmark Fotos", namePicture+".jpg"));
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_cameraFragment_to_mainFragment);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CapturePhotoError", "Capture request failed with reason: " + exception.getMessage());
                Toast.makeText(getContext(), "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    abstract protected void successfulTakePicture(String namePicture, Long timestamp, File file);
    abstract protected String getComplementName();
}
