package com.eduard05.ocr.ui;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class chooseDirectory extends Fragment {
    private static final int REQUEST_CODE_PICK_DIRECTORY = 1;
    private ActivityResultLauncher<Intent> pickDirectoryLaucher;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pickDirectoryLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == REQUEST_CODE_PICK_DIRECTORY && result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            Uri directoryUri = result.getData().getData();

                            getActivity().getContentResolver().takePersistableUriPermission(
                                    directoryUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            );

                            listFilesInDirectory(directoryUri);
                        }
                    }
                }
        );
        this.pickDirectory();
    }


    private void pickDirectory(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        this.pickDirectoryLaucher.launch(intent);
    }


    private void listFilesInDirectory(Uri directoryUri) {
        ContentResolver contentResolver = getActivity().getContentResolver();

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

                    Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(directoryUri, documentId);
                    Log.d("Document", "Name: " + displayName + ", MimeType: " + mimeType + ", Uri: " + documentUri.toString());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR IN GET FILE PATH", e.getMessage());
        }
    }
}
