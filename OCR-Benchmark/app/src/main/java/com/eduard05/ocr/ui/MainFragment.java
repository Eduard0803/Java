package com.eduard05.ocr.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.eduard05.ocr.R;
import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.eduard05.ocr.data.local.db.localRepositories.RepositoryOCRFrames;
import com.eduard05.ocr.data.local.db.localRepositories.RepositoryOCRResult;
import com.eduard05.ocr.util.Util;
import com.eduard05.ocr.works.BackgroundTaskManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private final String[]  PERMISSIONS =  {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
    };

    private ActivityResultLauncher<Intent> pickDirectoryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        TextView processTime = view.findViewById(R.id.text_view_process_time);
        processTime.setVisibility(View.GONE);
        RepositoryOCRResult repositoryOCRResult = new RepositoryOCRResult(getContext());
        repositoryOCRResult.getTotalExecTime().observe((LifecycleOwner) getContext(), new Observer<Long>() {
            @Override
            public void onChanged(Long time) {
                processTime.setText((time == null ? 0 : (time / 1_000_000.0) / 60)+ " m");
            }
        });

        TextView countFrames = view.findViewById(R.id.text_view_process_frames_count);
        RepositoryOCRFrames repositoryOCRFrames = new RepositoryOCRFrames(getContext());
        repositoryOCRFrames.getCountFrames().observe((LifecycleOwner) getContext(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                countFrames.setText((count == null ? 0 : count) + " Frames");
            }
        });

        Button btnSelectDir = view.findViewById(R.id.btn_navigate);
        btnSelectDir.setOnClickListener(v -> pickDirectory());

        Button btnCamera = view.findViewById(R.id.btn_to_camera);
        btnCamera.setOnClickListener(v -> redirectToCamera());

        Button btnExportResults = view.findViewById(R.id.btn_to_export_result);
        btnExportResults.setOnClickListener(v -> exportResults());

        Button btnClearResults = view.findViewById(R.id.btn_to_clear_results);
        btnClearResults.setOnClickListener(v -> {
            new RepositoryOCRResult(getContext()).clearAll();
            new RepositoryOCRFrames(getContext()).clearAll();

            File directory = new File(Environment.getExternalStorageDirectory() + "/OCR Benchmark Results/");
            if (!directory.exists()) {
                boolean hasCreated = directory.mkdirs();
                Log.d("DEBUG", "hasCreated --> " + hasCreated);
            }

            File fileOutput = new File(directory, "result.json.txt");
            fileOutput.delete();
        });

        pickDirectoryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == -1 && result.getData() != null) {
                        Uri directoryUri = result.getData().getData();

                        getActivity().getContentResolver().takePersistableUriPermission(
                                directoryUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        );
                        listFilesInDirectory(directoryUri);
                    }
                }
        );

        Util.requestPermission(PERMISSIONS, this);

        return view;
    }

    private void redirectToCamera(){
        Log.d("TAKE PICTURE", "BUTTON `TAKE PICTURE` HAS CLICKED");
        NavHostFragment.findNavController(MainFragment.this).navigate(R.id.camera_fragment);
    }

    private void exportResults(){
        List<String> output = new ArrayList<>();
        List<OCRResultDTO> results = new RepositoryOCRResult(getContext()).getAll();

        results.forEach(r -> {
            output.add(
                    "{" +
                            "\n\"idFrame\": \"" + r.getIdFrame() + "\"," +
                            "\n\"framePath\": \"" + r.getFramePath() + "\"," +
                            "\n\"processingTime\": \"" + r.getProcessingTime() + "\"," +
                            "\n\"angle\": " + r.getAngle() + "," +
                            "\n\"confidence\": " + r.getConfidence() + "," +
                            "\n\"text\": \"" + r.getText() + "\"," +
                            "\n\"BoundingBox.bottom\": " + r.getBottom() + "," +
                            "\n\"BoundingBox.left\": " + r.getLeft() + "," +
                            "\n\"BoundingBox.right\": " + r.getRight() + "," +
                            "\n\"BoundingBox.top\": " + r.getTop() +
                            "\n}"
            );
        });


        File directory = new File(Environment.getExternalStorageDirectory() + "/OCR Benchmark Results/");
        if (!directory.exists()) {
            boolean hasCreated = directory.mkdirs();
            Log.d("DEBUG", "hasCreated --> " + hasCreated);
        }

        File fileOutput = new File(directory, "result.json.txt");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(fileOutput);
            String strOutput = String.join(",\n", output);
            strOutput = "[" + strOutput + "]";
            fos.write(strOutput.getBytes());
            fos.flush();
            Toast.makeText(getContext(), "CREATED OUTPUT FILE IN 'OCR BENCHMARK RESULTS/result.json.txt' --> ", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            Log.e("DEBUG", "ERROR TO CREATE OUTPUT FILE --> " + e.getMessage());
            Toast.makeText(getContext(), "ERROR TO CREATE OUTPUT FILE --> " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void pickDirectory(){
        new RepositoryOCRResult(getContext()).clearAll();
        new RepositoryOCRFrames(getContext()).clearAll();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        pickDirectoryLauncher.launch(intent);
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
                    BackgroundTaskManager.createOCRProcessor(getContext(), documentUri.toString());
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
