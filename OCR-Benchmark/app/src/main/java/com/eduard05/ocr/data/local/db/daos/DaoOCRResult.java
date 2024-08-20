package com.eduard05.ocr.data.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.eduard05.ocr.data.local.db.entity.OCRResult;

@Dao
public interface DaoOCRResult extends DaoBase<OCRResult>{

    @Query("SELECT " +
            "(SELECT MAX(ocr.timeEnd) FROM OCRResult ocr) - (SELECT MIN(ocr.timeInit) FROM OCRResult ocr) " +
            "AS totalExecTime")
    LiveData<Long> getTotalExecTime();

    @Query("DELETE FROM OCRResult")
    void clearAll();
}
