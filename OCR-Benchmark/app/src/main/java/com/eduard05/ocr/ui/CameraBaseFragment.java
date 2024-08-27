package com.eduard05.ocr.ui;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private ImageView point1, point2, point3, point4;

    private Handler captureHandler;
    private Runnable captureRunnable;

    private long countPhotos = 0;
    private long LIMIT_PHOTOS = 5;

    public CameraBaseFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captureHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onStart(){
        super.onStart();
        this.countPhotos = 0;
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
    public void onResume() {
        super.onResume();
        requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requireActivity().getWindow().getDecorView().setFocusableInTouchMode(true);
        requireActivity().getWindow().getDecorView().requestFocus();
        requireActivity().getWindow().getDecorView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                        initCapture();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().getWindow().getDecorView().setOnKeyListener(null);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cameraView = inflater.inflate(R.layout.fragment_camera, container, false);

        this.btnCapture = cameraView.findViewById(R.id.btn_camera_capture);
        this.btnCapture.setOnClickListener(v -> initCapture());

        this.btnStop = cameraView.findViewById(R.id.btn_stop_scan);
        this.btnStop.setOnClickListener(v -> stopCapture());

        this.previewView = cameraView.findViewById(R.id.previewView);
        this.cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        this.point1 = cameraView.findViewById(R.id.point1);
        this.point2 = cameraView.findViewById(R.id.point2);
        this.point3 = cameraView.findViewById(R.id.point3);
        this.point4 = cameraView.findViewById(R.id.point4);
        
        setTouchListener(point1, point2, point3);
        setTouchListener(point2, point1, point4);
        setTouchListener(point3, point4, point1);
        setTouchListener(point4, point3, point2);

        Util.requestPermission(PERMISSIONS, this);

        return cameraView;
    }

    private void initCapture(){
        this.isRecording = true;
        this.btnCapture.setVisibility(View.GONE);
        this.btnStop.setVisibility(View.VISIBLE);

        captureRunnable = new Runnable() {
            @Override
            public void run() {
                if(isRecording){
                    capturePhoto();
                    captureHandler.postDelayed(this, 100);
                }
            }
        };
        captureHandler.post(captureRunnable);
    }

    private void stopCapture(){
        this.countPhotos = 0;
        this.isRecording = false;
        captureHandler.removeCallbacks(captureRunnable);
        this.btnCapture.setVisibility(View.VISIBLE);
        this.btnStop.setVisibility(View.GONE);
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

    private void capturePhoto(){
        this.countPhotos++;
        if(countPhotos == LIMIT_PHOTOS)
            stopCapture();
        long timestamp = Util.getTime();
        String namePicture = timestamp + "";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, namePicture);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/OCR Benchmark");
        }

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder( getActivity().getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues ).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Log.d("outputFileResults", outputFileResults.getSavedUri().getPath());
                Log.d("POINTERS", "P1 x = " + point1.getX() + " y = " + point1.getY());
                Log.d("POINTERS", "P2 x = " + point2.getX() + " y = " + point2.getY());
                Log.d("POINTERS", "P3 x = " + point3.getX() + " y = " + point3.getY());
                Log.d("POINTERS", "P4 x = " + point4.getX() + " y = " + point4.getY());
                successfulTakePicture(namePicture, timestamp,
                        new File(Environment.getExternalStorageDirectory() + "/Pictures/OCR Benchmark", namePicture + ".jpg"),
                        new double[] {point1.getX(), point2.getX(), point3.getX(), point4.getX()},
                        new double[] {point1.getY(), point2.getY(), point3.getY(), point4.getY()}
                );
//                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_cameraFragment_to_mainFragment);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CapturePhotoError", "Capture request failed with reason: " + exception.getMessage());
                Toast.makeText(getContext(), "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTouchListener(final ImageView point, final ImageView horizontalPoint, final ImageView verticalPoint) {
        point.setOnTouchListener(new View.OnTouchListener() {
            private float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + dX;
                        float newY = event.getRawY() + dY;

                        view.animate().x(newX).y(newY).setDuration(0).start();

                        horizontalPoint.animate().y(newY).setDuration(0).start();

                        verticalPoint.animate().x(newX).setDuration(0).start();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    abstract protected void successfulTakePicture(String namePicture, Long timestamp, File file, double[] x, double[] y);
    abstract protected String getComplementName();
}
