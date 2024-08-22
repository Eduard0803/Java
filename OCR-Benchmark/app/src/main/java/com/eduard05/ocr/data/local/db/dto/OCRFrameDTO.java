package com.eduard05.ocr.data.local.db.dto;

public class OCRFrameDTO {
    private int id;

    private String framePath;

    public OCRFrameDTO(){}
    public OCRFrameDTO(int id, String framePath){
        this.id = id;
        this.framePath = framePath;
    }

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}
    public String getFramePath(){return this.framePath;}
    public void setFramePath(String filePath){this.framePath = filePath;}
}
