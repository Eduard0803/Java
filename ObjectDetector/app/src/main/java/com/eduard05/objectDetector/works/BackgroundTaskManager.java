package com.eduard05.objectDetector.works;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class BackgroundTaskManager {
    public static void createObjectDetectionProcessor(Context context, String filePath){
        Log.d("OBJECT DETECTOR PROCESSOR", "CREATING WORKER");
        OneTimeWorkRequest objectDetectorWorkRequest = new OneTimeWorkRequest.Builder(ObjectDetectionWorker.class)
                .setInputData(new Data.Builder()
                        .putString("filePath", filePath)
                        .build()
                )
                .addTag(ObjectDetectionWorker.class.getName())
                .build();
        Log.d("OBJECT DETECTOR PROCESSOR", "ENQUEUE WORKER");
        WorkManager.getInstance(context).enqueue(objectDetectorWorkRequest);
    }
}
