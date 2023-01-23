package com.example.lab23a.PDF;

import com.example.lab23a.PDF.PDFConvertor;
import com.example.lab23a.model.Dates;
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.StudyTerm;
import com.example.lab23a.model.TermList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

class PDFConvertorTest {

    @Test
    void testImage() {
        TermList studyTerms = genTermList();
        SetIndex index = initIndex(studyTerms);
        PDFConvertor convertor = new PDFConvertor();
        try {
            PDDocument document = convertor.convert(new PDFParams(), index, studyTerms);
            document.save("test.pdf");
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    private TermList genTermList() {
        StudyTerm[] terms = new StudyTerm[3];
        terms[0] = new StudyTerm(0, "Hello", "Bonjour");
        terms[1] = new StudyTerm(1, "Bar", "Foo");
        terms[2] = new StudyTerm(2, "Some long text that won't fit the table cell", "One");
        return new TermList(terms);
    }
    private SetIndex initIndex(TermList list) {
        SetIndex index = new SetIndex(3);
        index.setName("Test");
        index.setElementsCount(list.size());
        index.setCreatedDate(Dates.currentDate());
        return index;
    }
}