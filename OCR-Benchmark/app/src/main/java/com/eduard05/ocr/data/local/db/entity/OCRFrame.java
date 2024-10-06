package com.eduard05.ocr.data.local.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OCRFrame {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String framePath;
    private long startTime;
    private long endTime;

    public OCRFrame(){}
    public OCRFrame(String framePath, long startTime, long endTime){
        this.framePath = framePath;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId(){return this.id;}
    public void setId(int id){this.id = id;}
    public String getFramePath(){return this.framePath;}
    public void setFramePath(String framePath){this.framePath = framePath;}

    public long getStartTime(){return this.startTime;}
    public void setStartTime(long startTime){this.startTime = startTime;}

    public long getEndTime(){return this.endTime;}
    public void setEndTime(long endTime){this.endTime = endTime;}
}
