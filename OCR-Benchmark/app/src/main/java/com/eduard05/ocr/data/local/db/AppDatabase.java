package com.eduard05.ocr.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.eduard05.ocr.data.local.db.daos.DaoOCRResult;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

@Database(entities = {
        OCRResult.class,
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoOCRResult daoOCRResult();
}
