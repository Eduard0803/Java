package com.eduard05.ocr.data.local.db.localRepositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.eduard05.ocr.data.local.db.dto.OCRFrameDTO;
import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.eduard05.ocr.data.local.db.entity.OCRResult;

import java.util.List;

public class RepositoryOCRResult extends Repository<OCRResult>{
    public RepositoryOCRResult(Context context){
        super(context);
        setDao(db.daoOCRResult());
    }

    public LiveData<Long> getTotalExecTime(){return db.daoOCRResult().getTotalExecTime();}

    public LiveData<Long> getAvgExecTime(){return db.daoOCRResult().getAvgExecTime();}

    public void clearAll(){db.daoOCRResult().clearAll();}

    public List<OCRFrameDTO> getAll(){
        List<OCRFrameDTO> listFrames = db.daoOCRResult().getAllFrames();

        listFrames.forEach(frame -> {
            int idFrame = frame.getId();
            List<OCRResultDTO> results =  db.daoOCRResult().getAllResultsFromFrames(idFrame);
            frame.setResultItems(results);
        });

        return listFrames;
    }
}
