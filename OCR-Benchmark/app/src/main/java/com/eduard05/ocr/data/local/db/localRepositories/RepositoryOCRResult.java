package com.eduard05.ocr.data.local.db.localRepositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

import java.util.List;

public class RepositoryOCRResult extends Repository<OCRResult>{
    public RepositoryOCRResult(Context context){
        super(context);
        setDao(db.daoOCRResult());
    }

    public LiveData<Long> getTotalExecTime(){return db.daoOCRResult().getTotalExecTime();}
    public void clearAll(){db.daoOCRResult().clearAll();}
    public List<OCRResultDTO> getAll(){return db.daoOCRResult().getAll();}
}
