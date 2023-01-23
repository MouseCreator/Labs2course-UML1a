package com.example.lab23a.PDF;

import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.TermList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;

public class PDFConvertor {

    public PDFConvertor() {}

    private PDFContent content;
    private PDDocument document;
    private SetIndex index;

    private PDFBounds bounds;
    public PDDocument convert(PDFParams params,SetIndex index, TermList terms) throws IOException {
        bounds = new PDFBounds(terms, params);
        this.index = index;
        this.write();
        return document;
    }


    public void write() throws IOException {
        this.document = new PDDocument();
        try {
            content = new PDFContent(document, bounds);
            resetColor();
            writeContent();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            content.close();
        }
    }
    private void writeContent() {
        createHeader();
        createTable();
        createFooter();
        document.addPage(content.getPage());
    }

    private void createFooter() {
        try {
            Rectangle rect = new Rectangle(0, bounds.getFooterHeight(),
                    (int) bounds.getWidth(), -bounds.getFooterHeight());
            fillRect(rect, bounds.getPalette().getFooter());
            resetColor();
        } catch (Exception e) {e.printStackTrace();}
    }

    private void fillRect(Rectangle rect, Color color) throws IOException {
        content.stream().addRect(rect.x, rect.y, rect.width, rect.height);
        content.stream().setNonStrokingColor(color);
        content.stream().fill();
    }

    private void drawRect(Rectangle rect) throws IOException {
        content.stream().addRect(rect.x, rect.y, rect.width, rect.height);
        content.stream().setStrokingColor(Color.BLACK);
        content.stream().stroke();
    }
    private void resetColor() throws IOException {
        content.stream().setStrokingColor(Color.BLACK);
        content.stream().setNonStrokingColor(Color.BLACK);
    }
    private void insertBoxInHeader() throws IOException {
        Rectangle rect = new Rectangle(0, (int) bounds.getHeaderHeight()-5, (int) bounds.getWidth(), 24);
        fillRect(rect, bounds.getPalette().getHeader());
        drawRect(rect);
        resetColor();
    }
    private void createHeader() {
        try {
            addImage();
            insertBoxInHeader();
            addSetTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSetTitle() throws IOException {
        content.stream().setNonStrokingColor(bounds.getPalette().getTextColor());
        content.stream().beginText();
        content.stream().setFont(PDType1Font.TIMES_BOLD, 20);
        content.stream().newLineAtOffset(30, bounds.getHeaderHeight());
        content.stream().showText(index.getNameShort(30));
        content.stream().endText();
        resetColor();
    }

    private void addImage() throws IOException {
        PDImageXObject image1 = PDImageXObject.
                createFromFile(FileBuilder.getImagePNG("Termverse128"), document);
        content.stream().drawImage(image1, 10, bounds.getHeight()-32);
    }
    private void createTable() {
        try {
            drawTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void drawTable() throws IOException {

        float position = addTableTitle(bounds.getPalette().getLegend());
        content.setStroke(bounds.getFontSize());
        for (PDFDoubleCell doubleCell : bounds.getDoubleCells()) {
            addRow(doubleCell, position, bounds.getPalette().getBackground());
            position -= doubleCell.getHeight();
        }
        content.stream().stroke();
    }
    private float addTableTitle(Color color) throws IOException {
        float position = bounds.getTablePosition();
        content.setBoldStroke(bounds.getFontSize());
        PDFDoubleCell titleCell = createTitleCell();
        addRow(titleCell, position, color);
        return position - titleCell.getHeight();
    }
    private PDFDoubleCell createTitleCell() {
        return new PDFDoubleCell(new PDFCell(bounds.getCellWidth(), bounds.getFontSize(),"Term"),
                new PDFCell(bounds.getCellWidth(), bounds.getFontSize(), "Definition"));
    }


    private void addRow(PDFDoubleCell doubleCell, float YPos, Color color) {
        try {
            addCell(bounds.getTabulationForTerm(), YPos, doubleCell.getTerm(), doubleCell.getHeight(), color);
            addCell(bounds.getTabulationForDefinition(), YPos, doubleCell.getDefinition(), doubleCell.getHeight(), color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCell(float currentX, float currentY, PDFCell cell, float height, Color color) throws IOException {
        Rectangle rectangle = new Rectangle((int) currentX, (int) currentY, (int) cell.getWidth(), (int) -height);
        fillRect(rectangle, color);
        drawRect(rectangle);
        resetColor();
        writeCell(currentX, currentY, cell);
    }
    private void writeCell(float currentX, float currentY, PDFCell cell) {
        try {
            for (String text : cell.getLines()) {
                content.stream().setNonStrokingColor(bounds.getPalette().getTextColor());
                content.stream().beginText();
                float textOffsetY = 3.0F;
                float textOffsetX = 5.0F;
                content.stream().newLineAtOffset(currentX + textOffsetX,
                        currentY - textOffsetY - bounds.getFontSize());
                content.stream().showText(text);
                content.stream().endText();
                resetColor();
                currentY -= bounds.getHeightBetweenLines();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
