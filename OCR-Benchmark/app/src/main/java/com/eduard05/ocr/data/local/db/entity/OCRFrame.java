package com.eduard05.ocr.data.local.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OCRFrame {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String framePath;

    public OCRFrame(){}
    public OCRFrame(String framePath){
        this.framePath = framePath;
    }

    public Integer getId(){return this.id;}
    public void setId(int id){this.id = id;}
    public String getFramePath(){return this.framePath;}
    public void setFramePath(String framePath){this.framePath = framePath;}
}
