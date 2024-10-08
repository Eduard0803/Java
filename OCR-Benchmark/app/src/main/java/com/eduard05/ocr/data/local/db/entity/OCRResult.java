package com.eduard05.ocr.data.local.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = OCRFrame.class, parentColumns = "id", childColumns = "idFrame")
        }
)
public class OCRResult {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private int idFrame;

    private double angle;
    private double confidence;
    private String text;
    private int boundingBoxBottom;
    private int boundingBoxTop;
    private int boundingBoxLeft;
    private int boundingBoxRight;

    public OCRResult(){}

    public OCRResult(int idFrame, double angle, double confidence, String text, int bottom, int top, int right, int left){
        this.idFrame = idFrame;
        this.angle = angle;
        this.confidence = confidence;
        this.text = text;
        this.boundingBoxBottom = bottom;
        this.boundingBoxTop = top;
        this.boundingBoxLeft = left;
        this.boundingBoxRight = right;
    }

    public int getIdFrame(){return this.idFrame;}
    public void setIdFrame(int idFrame){this.idFrame = idFrame;}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getBoundingBoxBottom() { return boundingBoxBottom; }
    public void setBoundingBoxBottom(int boundingBoxBottom) { this.boundingBoxBottom = boundingBoxBottom; }

    public int getBoundingBoxTop() { return boundingBoxTop; }
    public void setBoundingBoxTop(int boundingBoxTop) { this.boundingBoxTop = boundingBoxTop; }

    public int getBoundingBoxLeft() { return boundingBoxLeft; }
    public void setBoundingBoxLeft(int boundingBoxLeft) { this.boundingBoxLeft = boundingBoxLeft; }

    public int getBoundingBoxRight() { return boundingBoxRight; }
    public void setBoundingBoxRight(int boundingBoxRight) { this.boundingBoxRight = boundingBoxRight; }
}
