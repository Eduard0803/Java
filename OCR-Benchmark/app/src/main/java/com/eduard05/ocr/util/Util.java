package com.eduard05.ocr.util;

import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;

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
}
