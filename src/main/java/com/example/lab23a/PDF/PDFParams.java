package com.example.lab23a.PDF;

public class PDFParams {
    private int fontSize = 14;
    public int getFontSize() {
        return fontSize;
    }
    public PDFParams() {
        this.palette = new PDFPalette();
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    private final PDFPalette palette;
    public PDFPalette getPalette() {
        return palette;
    }
}
