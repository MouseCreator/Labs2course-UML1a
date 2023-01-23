package com.example.lab23a.PDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.Closeable;
import java.io.IOException;


public class PDFContent implements Closeable {
    private final PDPageContentStream pageStream;
    private final PDPage page;
    public PDFContent(PDDocument document, PDFBounds bounds) throws IOException {
        this.page = initPage(bounds);
        pageStream = new PDPageContentStream(document, page);
    }
    private PDPage initPage(PDFBounds bounds) {
        PDRectangle sizeSetter = new PDRectangle(bounds.getWidth(), bounds.getHeight());
        return new PDPage(sizeSetter);
    }
    public PDPageContentStream stream() {
        return pageStream;
    }

    @Override
    public void close() throws IOException {
        pageStream.close();
    }

    public PDPage getPage() {
        return page;
    }

    public void setStroke(int font) throws IOException{
        pageStream.setStrokingColor(Color.BLACK);
        pageStream.setLineWidth(1);
        pageStream.setFont(PDType1Font.TIMES_ROMAN, font);
    }

    public void setBoldStroke(int fontSize) throws IOException{
        pageStream.setStrokingColor(Color.BLACK);
        pageStream.setLineWidth(1);
        pageStream.setFont(PDType1Font.TIMES_BOLD, fontSize);
    }
}
