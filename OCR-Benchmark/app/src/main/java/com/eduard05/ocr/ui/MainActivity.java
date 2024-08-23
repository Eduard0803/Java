package com.eduard05.ocr.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.eduard05.ocr.R;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickDirectoryLauncher;
    private NavController navController;
    private FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.navController = ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView)).getNavController();

        this.fragmentContainerView = findViewById(R.id.fragmentContainerView);
    }
}
