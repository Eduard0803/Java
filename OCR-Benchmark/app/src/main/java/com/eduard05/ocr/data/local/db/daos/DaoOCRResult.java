package com.eduard05.ocr.data.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.eduard05.ocr.data.local.db.entity.OCRResult;

@Dao
public interface DaoOCRResult extends DaoBase<OCRResult>{

    @Query("SELECT " +
            "(SELECT ocr.timeEnd FROM OCRResult ocr ORDER BY ocr.timeEnd DESC LIMIT 1) - (SELECT ocr.timeInit FROM OCRResult ocr ORDER BY ocr.timeInit LIMIT 1) " +
            "AS totalExecTime")
    LiveData<Long> getTotalExecTime();

    @Query("DELETE FROM OCRResult")
    void clearAll();
}
