package com.eduard05.ocr.data.local.db.dto;


public class OCRResultDTO {

    private double angle;
    private double confidence;
    private String text;
    private int top;
    private int bottom;
    private int right;
    private int left;


    public OCRResultDTO(double angle, double confidence, String text, int top, int bottom, int right, int left){
        this.angle = angle;
        this.confidence = confidence;
        this.text = text;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

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
}
