package com.eduard05.ocr.util;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.mlkit.vision.objects.DetectedObject;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static void requestPermission(String[]  PERMISSIONS, Fragment fragment){

        // Does Camera permission already granted ?
        if ( Arrays.stream(PERMISSIONS).allMatch(p -> ContextCompat.checkSelfPermission( fragment.getContext(), p) == PackageManager.PERMISSION_GRANTED)){
            // You can use the API that requires the permission.

        } else {

            ActivityResultLauncher<String[]> requestPermissionLauncher =
                    fragment.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                        if (isGranted.values().stream().allMatch(x -> x)) {
                            // Permission is granted. Continue the action or workflow in your
                            // app.
                        } else {
                            // Explain to the user that the feature is unavailable because the
                            // features requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                        }
                    });

            requestPermissionLauncher.launch(PERMISSIONS);
        }
    }

    public static long getTime(){
        Clock clock = Clock.systemUTC();
        Instant instant = clock.instant();

        return  instant.getEpochSecond() * 1_000_000L + instant.getNano() / 1_000;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, double degrees){
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap drawBoundingBoxesOnBitmap(Bitmap bitmap, List<DetectedObject> detectedObjects) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(50);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);

        for (DetectedObject detectedObject : detectedObjects) {
            Rect boundingBox = detectedObject.getBoundingBox();
            canvas.drawRect(boundingBox, paint);

            if (!detectedObject.getLabels().isEmpty()) {
                DetectedObject.Label label = detectedObject.getLabels().get(0);
                String labelText = label.getText();
                float confidence = label.getConfidence();

                canvas.drawText(labelText + " (" + String.format("%.2f", confidence * 100) + "%)",
                        boundingBox.left, boundingBox.top - 10, textPaint);
            }
        }

        return rotateBitmap(mutableBitmap, 90);
    }

    public static boolean writeBitmapOnDisk(Bitmap bitmap, String path, Bitmap.CompressFormat format, int quality){
        File outputFile = new File(Environment.getExternalStorageDirectory(), path);
        try{
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(format, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch(Exception e){
            Log.e("SAVE FILE", "ERROR TO SAVE FILE --> " + e.getMessage());
            return false;
        }
        return true;
    }
}
