package com.eduard05.ocr.data.local.db.localRepositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.eduard05.ocr.data.local.db.entity.OCRResult;

public class RepositoryOCR extends Repository<OCRResult>{
    public RepositoryOCR(Context context){
        super(context);
        setDao(db.daoOCRResult());
    }

    public LiveData<Long> getTotalExecTime(){return db.daoOCRResult().getTotalExecTime();}
    public void clearAll(){db.daoOCRResult().clearAll();}
}
