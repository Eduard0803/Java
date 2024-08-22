package com.eduard05.ocr.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.eduard05.ocr.R;
import com.eduard05.ocr.data.local.db.localRepositories.RepositoryOCRFrames;
import com.eduard05.ocr.data.local.db.localRepositories.RepositoryOCRResult;
import com.eduard05.ocr.works.BackgroundTaskManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> pickDirectoryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView processTime = findViewById(R.id.text_view_process_time);
//        processTime.setVisibility(View.GONE);
        RepositoryOCRResult repositoryOCRResult = new RepositoryOCRResult(getApplicationContext());
        repositoryOCRResult.getTotalExecTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                processTime.setText((time == null ? 0 : (time / 1_000_000.0) / 60)+ " m");
            }
        });

        TextView countFrames = findViewById(R.id.text_view_process_frames_count);
        RepositoryOCRFrames repositoryOCRFrames = new RepositoryOCRFrames(getApplicationContext());
        repositoryOCRFrames.getCountFrames().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                countFrames.setText((count == null ? 0 : count) + " Frames");
            }
        });


        Button btnSelectDir = findViewById(R.id.btn_navigate);
        btnSelectDir.setOnClickListener(view -> pickDirectory());

        pickDirectoryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri directoryUri = result.getData().getData();

                        getContentResolver().takePersistableUriPermission(
                                directoryUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        );
                        listFilesInDirectory(directoryUri);
                    }
                }
        );
    }

    private void pickDirectory(){
        new RepositoryOCRResult(getApplicationContext()).clearAll();
        new RepositoryOCRFrames(getApplicationContext()).clearAll();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        pickDirectoryLauncher.launch(intent);
    }

    private void listFilesInDirectory(Uri directoryUri) {
        ContentResolver contentResolver = getContentResolver();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        Log.d("GOOGLE PLAY", "" + resultCode + " " + (resultCode == ConnectionResult.SUCCESS));

        String[] projection = new String[]{
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE,
                DocumentsContract.Document.COLUMN_DOCUMENT_ID
        };

        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                directoryUri,
                DocumentsContract.getTreeDocumentId(directoryUri)
        );

        try (Cursor cursor = contentResolver.query(childrenUri, projection, null, null, null)) {
            if (cursor != null) {
                int nameIdx = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
                int mimeIdx = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE);
                int idIdx = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID);

                long timeInit = System.currentTimeMillis();
                long count = 0;
                while (cursor.moveToNext()) {
                    if(nameIdx == -1 || mimeIdx == -1 || idIdx == -1) {
                        Log.d("ERROR IN GET FILE", "NAME IDX --> " + nameIdx);
                        Log.d("ERROR IN GET FILE", "MIME IDX --> " + mimeIdx);
                        Log.d("ERROR IN GET FILE", "ID IDX --> " + idIdx);
                        continue;
                    }
                    String displayName = cursor.getString(nameIdx);
                    String mimeType = cursor.getString(mimeIdx);
                    String documentId = cursor.getString(idIdx);

                    if(!mimeType.split("/")[0].equals("image"))
                        continue;
                    count++;

                    Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(directoryUri, documentId);
                    Log.d("Document", "Name: " + displayName + ", MimeType: " + mimeType + ", Uri: " + documentUri.toString());
                    BackgroundTaskManager.createOCRProcessor(getApplicationContext(), documentUri.toString());
                }

                long timeEnd = System.currentTimeMillis();
                Log.d("OCR PROCESSING TIME", "TOTAL TIME --> " + (timeEnd - timeInit));
                Log.d("OCR PROCESSING TIME", "FRAMES COUNTS --> " + count);
            }
        } catch (Exception e) {
            Log.d("ERROR IN GET FILE PATH", e.getMessage());
        }
    }
}
