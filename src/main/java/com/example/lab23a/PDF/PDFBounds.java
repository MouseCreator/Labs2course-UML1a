package com.example.lab23a.PDF;

import com.example.lab23a.model.TermList;

public class PDFBounds {
    private final int defaultHeight = 792;
    private final int defaultWidth = 612;
    private final int headerHeight = 60;
    private final int footerHeight = 40;
    private int height = defaultHeight;

    private final PDFDoubleCell[] doubleCells;

    public int getFontSize() {
        return fontSize;
    }
    public float getHeightBetweenLines() {
        return lineHeight;
    }
    private final int fontSize;
    private final int lineHeight;
    private final int cellWidth;


    public int getCellWidth() {
        return cellWidth;
    }
    private final int tabulationWidth = 20;

    public PDFDoubleCell[] getDoubleCells() {
        return this.doubleCells;
    }
    public PDFBounds(TermList list, PDFParams params) {
        this.fontSize = params.getFontSize();
        this.cellWidth = defaultWidth / 2 - tabulationWidth * 2;

        int emptyCellSpace = 4;
        this.lineHeight = fontSize + emptyCellSpace;

        this.doubleCells = createDoubleCells(list);
        setHeight();
        this.palette = params.getPalette();

    }
    public final int getTabulationForTerm() {
        return tabulationWidth + 4;
    }
    public final int getTabulationForDefinition() {
        return tabulationWidth + cellWidth + 4;
    }
    private int tableHeight(PDFDoubleCell[] doubleCells) {
        int height = 0;
        for (PDFDoubleCell cell : doubleCells) {
            height += Math.max(cell.getDefinition().getHeight(), cell.getTerm().getHeight());
        }
        return height;
    }
    private int calculatedPageHeight() {

        return headerHeight + tableHeight(doubleCells) + footerHeight;
    }
    public int expectedHeight() {
        return Math.max(calculatedPageHeight(), defaultHeight);
    }

    public float getWidth() {
        return (float) this.defaultWidth;
    }
    private void setHeight() {
        int expected = this.expectedHeight();
        this.height = Math.max(expected, defaultHeight);
    }
    public int getHeight() {
        return this.height;
    }
    private PDFDoubleCell[] createDoubleCells(TermList terms) {
        PDFDoubleCell[] cells = new PDFDoubleCell[terms.size()];
        for (int i = 0; i < terms.size(); i++) {
            cells[i] = new PDFDoubleCell(this.cellWidth, this.fontSize, terms.getByIndex(i));
        }
        return cells;
    }

    public float getHeaderHeight() {
        return (float) this.height - headerHeight;
    }
    public float getTablePosition() {
        return (float) this.height - headerHeight - 10.f;
    }


    private final PDFPalette palette;
    public PDFPalette getPalette() {
        return palette;
    }

    public int getFooterHeight() {
        return footerHeight;
    }
}
