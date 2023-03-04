package com.example.lab23a.PDF;

import java.awt.*;

public class PDFPalette {
    public PDFPalette() {
        this.background = Color.white;
        this.footer = Color.white;
        this.legend = Color.white;
        this.header = Color.white;
    }
    private Color header;
    private Color background;
    private Color legend;
    private Color footer;

    private Color textColor;

    public Color getFooter() {
        return footer;
    }

    public void setFooter(Color footer) {
        this.footer = footer;
    }

    public Color getLegend() {
        return legend;
    }

    public void setLegend(Color legend) {
        this.legend = legend;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getHeader() {
        return header;
    }

    public void setHeader(Color header) {
        this.header = header;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }



}
