package com.eduard05.ocr.data.local.db.dto;

import java.util.List;

public class OCRFrameDTO {
    private int id;

    private String framePath;
    private long processingTime;
    private List<OCRResultDTO> resultItems;

    public OCRFrameDTO(){}
    public OCRFrameDTO(int id, String framePath, long processingTime){
        this.id = id;
        this.framePath = framePath;
        this.processingTime = processingTime;
    }

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}
    public String getFramePath(){return this.framePath;}
    public void setFramePath(String filePath){this.framePath = filePath;}

    public long getProcessingTime(){return processingTime;}
    public void setProcessingTime(long processingTime){this.processingTime = processingTime;}

    public List<OCRResultDTO> getResultItems(){return resultItems;}
    public void setResultItems(List<OCRResultDTO> resultItems){this.resultItems = resultItems;}
}
