package com.example.lab23a.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetIndexListTest {

    private SetIndexList sampleList;

    private final SetIndex index1 = new SetIndex(0, "Hello", 0);
    private final SetIndex index2 = new SetIndex(1, "Welcome", 0);
    private final SetIndex index3 = new SetIndex(1, "World", 0);
    private final SetIndex index4 = new SetIndex(1, "Goodbye", 0);

    @BeforeEach
    void generateSample() {
        sampleList = new SetIndexList();

        sampleList.add(index1);
        sampleList.add(index2);
        sampleList.add(index3);
        sampleList.add(index4);
    }
    @Test
    void testSearchSubstring() {
        SetIndexList result = sampleList.searchByName(index3.getName().substring(2));
        assertTrue(result.contains(index3));
    }
}