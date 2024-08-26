package com.eduard05.ocr.data.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

import java.util.List;

@Dao
public interface DaoOCRResult extends DaoBase<OCRResult>{

    @Query("SELECT " +
            "(SELECT ocr.timeEnd FROM OCRResult ocr ORDER BY ocr.timeEnd DESC LIMIT 1) - (SELECT ocr.timeInit FROM OCRResult ocr ORDER BY ocr.timeInit LIMIT 1) " +
            "AS totalExecTime")
    LiveData<Long> getTotalExecTime();

    @Query("DELETE FROM OCRResult")
    void clearAll();

    @Query("SELECT " +
            "ocrF.id as idFrame," +
            "ocrF.framePath, " +
            "ocrR.angle," +
            "ocrR.confidence," +
            "ocrR.text," +
            "ocrR.boundingBoxBottom AS bottom," +
            "ocrR.boundingBoxTop AS top," +
            "ocrR.boundingBoxRight AS 'right'," +
            "ocrR.boundingBoxLeft as 'left'," +
            "((ocrR.timeEnd - ocrR.timeInit) / 1000000.0) AS processingTime " +
            "FROM OCRFrame ocrF " +
            "JOIN OCRResult ocrR ON ocrF.id = ocrR.idFrame;")
    List<OCRResultDTO> getAll();
}
