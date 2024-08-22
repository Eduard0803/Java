package com.eduard05.ocr.works;

import android.content.Context;
import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BackgroundTaskManager {

    public static void createOCRProcessor(Context context, String filePath){
        Log.d("OCR PROCESSOR", "INIT METHOD `createOCRProcessor`");

        Log.d("OCR PROCESSOR", "SET UP CONSTRAINS");
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        Log.d("OCR PROCESSOR", "CREATING WORKER");
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(OCRProcessorWork.class)
                .setConstraints(constraints)
                .setInputData(new Data.Builder()
                        .putString("filePath", filePath)
                        .build()
                )
                .addTag(OCRProcessorWork.class.getName())
                .setBackoffCriteria(
                        BackoffPolicy.EXPONENTIAL,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS)
                .build();
        Log.d("OCR PROCESSOR", "ENQUEUE WORKER");
        WorkManager.getInstance(context).enqueue(uploadWorkRequest);
    }
}
