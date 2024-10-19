package com.eduard05.ocr.ui;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.eduard05.ocr.util.Util;
import com.eduard05.ocr.works.BackgroundTaskManager;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraObjectDetect extends CameraBaseFragment {

    private PreviewView previewView;
//    private ObjectOverlayView overlayView;
    private ExecutorService cameraExecutor;

    public CameraObjectDetect(){super();}


    @Override
    protected void successfulTakePicture(String namePicture, Long timestamp, File file) {
        BackgroundTaskManager.createObjectDetectionProcessor(getContext(), file.getAbsolutePath());
    }

    @Override
    protected String getComplementName(){return "";}
}
