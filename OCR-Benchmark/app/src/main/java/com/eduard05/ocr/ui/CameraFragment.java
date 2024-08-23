package com.eduard05.ocr.ui;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.eduard05.ocr.works.BackgroundTaskManager;

import java.io.File;

public class CameraFragment extends CameraBaseFragment {

    public CameraFragment(){
        super();
    }

    @Override
    protected void successfulTakePicture(String namePicture, Long timestamp, File file) {
        Log.d("sucessfulTakePicture", namePicture);

        Log.d("URI", "file.getPath() --> " + file.getPath());
        Log.d("URI", "file.getAbsolutePath() --> " + file.getAbsolutePath());
        Log.d("URI", "file.getParent() --> " + file.getParent());

        Log.d("URI", "FILE EXISTS? --> " + file.exists());
        Log.d("URI", "FILE CAN READ? --> " + file.canRead());

        Log.d("URI", "file.setReadable(true) --> " + file.setReadable(true));

        Log.d("URI", "FILE CAN READ AFTER? --> " + file.canRead());

        Uri uri = null;
        String[] projection = {MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.DATA + "=?";
        String[] selectionArgs = new String[]{file.getAbsolutePath()};

        Cursor cursor = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
        uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        cursor.close();

        BackgroundTaskManager.createOCRProcessor(getContext(), uri.toString());
    }

    @Override
    protected String getComplementName(){return "";}
}
