package com.eduard05.ocr.data.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.eduard05.ocr.data.local.db.dto.OCRFrameDTO;
import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

import java.util.List;

@Dao
public interface DaoOCRResult extends DaoBase<OCRResult>{

    @Query("SELECT " +
            "(SELECT ocrF.endTime FROM OCRFrame ocrF ORDER BY ocrF.endTime DESC LIMIT 1) - (SELECT ocrF.startTime FROM OCRFrame ocrF ORDER BY ocrF.startTime LIMIT 1) " +
            "AS totalExecTime")
    LiveData<Long> getTotalExecTime();

    @Query("SELECT " +
            "AVG(ocrF.endTime - ocrF.startTime) AS avgExecTime " +
            "FROM OCRFrame ocrF ")
    LiveData<Long> getAvgExecTime();

    @Query("DELETE FROM OCRResult")
    void clearAll();

    @Query("SELECT " +
            "ocrF.id, " +
            "ocrF.framePath, " +
            "AVG(ocrF.endTime - ocrF.startTime) AS processingTime " +
            "FROM OCRFrame ocrF " +
            "GROUP BY ocrF.id, ocrF.framePath")
    List<OCRFrameDTO> getAllFrames();

    @Query("SELECT ocrR.idFrame," +
            " ocrR.angle," +
            "ocrR.confidence," +
            "ocrR.text," +
            "ocrR.boundingBoxTop AS top," +
            "ocrR.boundingBoxBottom AS bottom," +
            "ocrR.boundingBoxRight AS 'right'," +
            "ocrR.boundingBoxLeft AS 'left' " +
            "FROM OCRResult ocrR WHERE ocrR.idFrame = :id;")
    List<OCRResultDTO> getAllResultsFromFrames(int id);
}
