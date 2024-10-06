package com.eduard05.ocr.ui;


import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.eduard05.ocr.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraObjectDetect extends Fragment {

    private PreviewView previewView;
//    private ObjectOverlayView overlayView;
    private ExecutorService cameraExecutor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        previewView = view.findViewById(R.id.previewView);
//        overlayView = view.findViewById(R.id.overlayView); // Reference to the overlay view
        cameraExecutor = Executors.newSingleThreadExecutor();

        startCamera();
        return view;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Preview
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Image Analysis for object detection
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
                    @SuppressLint("UnsafeOptInUsageError")
                    InputImage image = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

                    // Pass the image to ML Kit for object detection
                    detectObjects(image, imageProxy);
                });

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void detectObjects(InputImage image, ImageProxy imageProxy) {
        ObjectDetectorOptions options =
                new ObjectDetectorOptions.Builder()
                        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                        .enableMultipleObjects()
                        .enableClassification()
                        .build();

        ObjectDetector objectDetector = ObjectDetection.getClient(options);

        objectDetector.process(image)
                .addOnSuccessListener(detectedObjects -> {
                    List<Rect> boundingBoxes = new ArrayList<>();

                    for(DetectedObject detectedObject : detectedObjects) {
                        Rect boundingBox = detectedObject.getBoundingBox();
                        boundingBoxes.add(boundingBox);  // Add each detected object's bounding box

                        String label = detectedObject.getLabels().get(0).getText();
                        Log.d("label", label);
                    }

                    // Update the overlay with the bounding boxes
//                    overlayView.setBoundingBoxes(boundingBoxes);

                    imageProxy.close();  // Close the image proxy when done
                })
                .addOnFailureListener(e -> {
                    imageProxy.close();  // Close the image proxy even in case of failure
//                    e.printStackTrace();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
