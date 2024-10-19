package com.eduard05.ocr.ui;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        Log.d("DEBUG", "URI --> " + uri.getPath());

        BackgroundTaskManager.createOCRProcessor(getContext(), uri.toString());
    }

    @Override
    protected String getComplementName(){return "";}
}
