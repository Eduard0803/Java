package com.eduard05.ocr.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.eduard05.ocr.data.local.db.daos.DaoOCRFrame;
import com.eduard05.ocr.data.local.db.daos.DaoOCRResult;
import com.eduard05.ocr.data.local.db.entity.OCRFrame;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

@Database(entities = {
        OCRFrame.class,
        OCRResult.class,
}, version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoOCRFrame daoOCRFrame();
    public abstract DaoOCRResult daoOCRResult();
}
