package com.eduard05.objectDetector.ui;


import androidx.camera.view.PreviewView;

import com.eduard05.objectDetector.R;
import com.eduard05.objectDetector.works.BackgroundTaskManager;

import java.io.File;
import java.util.concurrent.ExecutorService;

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
