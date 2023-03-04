package com.example.lab23a.PDF;

import com.example.lab23a.model.StudyTerm;

public class PDFDoubleCell {
    private PDFCell term;
    private final PDFCell definition;
    public PDFDoubleCell(PDFCell term, PDFCell definition) {
        this.term = term;
        this.definition = definition;
    }
    public PDFDoubleCell(int cellWidth, int fontSize, StudyTerm term) {
        this.term = new PDFCell(cellWidth, fontSize, term.getTerm());
        this.definition = new PDFCell(cellWidth, fontSize, term.getDefinition());
    }
    public PDFCell getTerm() {
        return term;
    }

    public void setTerm(PDFCell term) {
        this.term = term;
    }

    public PDFCell getDefinition() {
        return definition;
    }

    public float getHeight() {
        return (float) Math.max(definition.getHeight(), term.getHeight());
    }
}
