package com.eduard05.ocr.data.local.db.localRepositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.eduard05.ocr.data.local.db.dto.OCRFrameDTO;
import com.eduard05.ocr.data.local.db.entity.OCRFrame;

public class RepositoryOCRFrames extends Repository<OCRFrame>{
    public RepositoryOCRFrames(Context context){
        super(context);
        setDao(db.daoOCRFrame());
    }

    public OCRFrameDTO getById(int id){return db.daoOCRFrame().getById(id);}

    public LiveData<Integer> getCountFrames(){return db.daoOCRFrame().getCountFrames();}
    public void clearAll(){db.daoOCRFrame().clearAll();}
}
