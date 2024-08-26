package com.eduard05.ocr.data.local.db.dto;


public class OCRResultDTO {
    private int idFrame;
    private String framePath;
    private double angle;
    private double confidence;
    private String text;
    private int top;
    private int bottom;
    private int right;
    private int left;
    private long processingTime;

    public OCRResultDTO(int idFrame, String framePath, double angle, double confidence, String text, int top, int bottom, int right, int left, long processingTime){
        this.idFrame = idFrame;
        this.framePath = framePath;
        this.angle = angle;
        this.confidence = confidence;
        this.text = text;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.processingTime = processingTime;
    }

    public int getIdFrame() { return idFrame; }
    public void setIdFrame(int idFrame) { this.idFrame = idFrame; }

    public String getFramePath() { return framePath; }
    public void setFramePath(String framePath) { this.framePath = framePath; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getTop() { return top; }
    public void setTop(int top) { this.top = top; }

    public int getBottom() { return bottom; }
    public void setBottom(int bottom) { this.bottom = bottom; }

    public int getRight() { return right; }
    public void setRight(int right) { this.right = right; }

    public int getLeft() { return left; }
    public void setLeft(int left) { this.left = left; }

    public long getProcessingTime(){return processingTime;}
    public void setProcessingTime(long processingTime){this.processingTime = processingTime;}
}
