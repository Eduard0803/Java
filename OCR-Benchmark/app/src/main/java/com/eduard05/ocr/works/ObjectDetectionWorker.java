package com.eduard05.ocr.works;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.eduard05.ocr.util.Util;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import java.io.File;

public class ObjectDetectionWorker extends Worker {

    public ObjectDetectionWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters){
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork(){
        String filePath = getInputData().getString("filePath");

        processObjectDetector(filePath);
        return Result.success();
    }

    public void processObjectDetector(String filePath){
        Uri uri = Uri.parse(filePath);

        try {
            File file = new File(uri.getPath());
            Bitmap imageBitmap = BitmapFactory.decodeFile(filePath);
            InputImage image = InputImage.fromBitmap(imageBitmap, 0);

            ObjectDetectorOptions options = new ObjectDetectorOptions.Builder()
                    .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                    .enableMultipleObjects()
                    .enableClassification()
                    .build();

            ObjectDetector objectDetector = ObjectDetection.getClient(options);

            objectDetector.process(image)
                    .addOnSuccessListener(detectedObjects -> {

                        Util.writeBitmapOnDisk(
                                Util.drawBoundingBoxesOnBitmap(imageBitmap, detectedObjects),
                                "/Pictures/OCR Benchmark/objectDetect_" + file.getName(),
                                Bitmap.CompressFormat.JPEG,
                                100
                        );

                        for (DetectedObject detectedObject : detectedObjects) {
                            Rect boudingBox = detectedObject.getBoundingBox();

                            for (DetectedObject.Label label : detectedObject.getLabels()) {
                                String text = label.getText();
                                float confidence = label.getConfidence();
                                Log.d("OBJECT DETECTOR", "OBJECT NAME: " + text);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("OBJECT DETECTOR", "ERROR ON OBJECT DETECTOR e: " + e.getMessage());
                    });
        } catch(Exception e){
            Log.e("OBJECT DETECTOR", "ERROR ON OBJECT DETECTOR e: " + e.getMessage());
        }
    }
}
