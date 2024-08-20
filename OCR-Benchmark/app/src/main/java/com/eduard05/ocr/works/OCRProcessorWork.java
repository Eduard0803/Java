package com.eduard05.ocr.works;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.eduard05.ocr.data.local.db.entity.OCRResult;
import com.eduard05.ocr.data.local.db.localRepositories.RepositoryOCR;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import io.reactivex.rxjava3.core.Completable;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class OCRProcessorWork extends Worker {

    public OCRProcessorWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String filePath = getInputData().getString("filePath");

        CountDownLatch latch = new CountDownLatch(1);
        final Result[] result = new Result[1];

        processOCR(filePath)
            .subscribe(
                () -> {
                    result[0] = Result.success();
                    latch.countDown();
                },
                throwable -> {
                    result[0] = Result.retry();
                    latch.countDown();
                }
            );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.retry();
        }

        return result[0];
    }

    public Completable processOCR(String filePath){
        Log.d("OCR PROCESSOR", "INIT METHOD `public Completable processOCR(String filePath)` TO FILE `" + filePath +"`");

//        File file = new File(filePath);
        Uri uri = Uri.parse(filePath);

        return Completable.create( emitter -> {
            long startTime = System.currentTimeMillis();

            InputImage image = InputImage.fromFilePath(getApplicationContext(), uri);

            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            recognizer.process(image)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text visionText) {
                            long endTime = System.currentTimeMillis();
                            long processingTime = endTime - startTime;
                            Log.d("OCR PROCESSING TIME", "Tempo de processamento: " + processingTime + " ms");

                            for ( Text.TextBlock tb: visionText.getTextBlocks())
                                for (Text.Line tl : tb.getLines() )
                                    for (Text.Element te : tl.getElements()) {
                                        Log.d(
                                                "OCR RESULT",
                                                "OCR RESULT{" +
                                                        "\nangle: " + te.getAngle() +
                                                        ",\nconfidence: " + te.getConfidence() +
                                                        ",\ntext: " + te.getText() +
                                                        ",\nBoundingBox.bottom: " + te.getBoundingBox().bottom +
                                                        ",\nBoundingBox.left: " + te.getBoundingBox().left +
                                                        ",\nBoundingBox.right: " + te.getBoundingBox().right +
                                                        ",\nBoundingBox.top: " + te.getBoundingBox().top +
                                                        "\n}"
                                        );
                                        new RepositoryOCR(getApplicationContext()).insert(new OCRResult(
                                                te.getAngle(), te.getConfidence(), te.getText(),
                                                te.getBoundingBox().bottom, te.getBoundingBox().top, te.getBoundingBox().right, te.getBoundingBox().left,
                                                startTime, endTime
                                        ));
                                    }
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    emitter.onError(new Throwable(e.getMessage()));
                                }
                            });
        });
    }
}
