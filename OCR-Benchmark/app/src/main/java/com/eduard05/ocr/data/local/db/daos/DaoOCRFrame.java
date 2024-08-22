package com.eduard05.ocr.data.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.eduard05.ocr.data.local.db.dto.OCRFrameDTO;
import com.eduard05.ocr.data.local.db.entity.OCRFrame;

@Dao
public interface DaoOCRFrame extends DaoBase<OCRFrame>{
    @Query("SELECT COUNT(id) AS countFrames FROM OCRFrame")
    LiveData<Integer> getCountFrames();

    @Query("SELECT ocr.id AS id, ocr.framePath AS framePath from OCRFrame ocr WHERE id = :id")
    OCRFrameDTO getById(int id);

    @Query("DELETE FROM OCRFrame")
    void clearAll();
}
