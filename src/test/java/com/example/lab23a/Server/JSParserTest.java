package com.example.lab23a.Server;

import com.example.lab23a.model.Dates;
import com.example.lab23a.model.SetIndex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSParserTest {
    @Test
    void testStudyIndex() {
        SetIndex index = new SetIndex(4, "SomeName", 10, Dates.currentDate(), Dates.currentDate());
        String from = JSParser.fromStudyIndex(index);
        System.out.println(from);
    }
}