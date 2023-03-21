package com.example.lab23a.model.builder;

import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    @Test
    void testForIndex() {
        ClassUnparser unparser = new ClassUnparser();
        SetIndex index = new SetIndex(1, "hello", 2);
        unparser.visitSetIndex(index);
        String result = (String) unparser.get();
        assertNotEquals("", result);
        System.out.println(result);
    }
    @Test
    void testForFolder() {
        Folder folder = new Folder(9, "folderName");
        ClassUnparser unparser = new ClassUnparser();
        String result = unparser.parseFolder(folder);
        assertNotEquals("", result);
        System.out.println(result);
    }

}