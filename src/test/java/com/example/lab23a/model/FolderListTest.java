package com.example.lab23a.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FolderListTest {

    private FolderList list;

    Folder[] arrayTestFolders = new Folder[4];
    @BeforeEach
    public void fillList() {
        list = new FolderList();

        arrayTestFolders[0] = new Folder(1, "C");
        arrayTestFolders[1] = new Folder(3, "B");
        arrayTestFolders[2] = new Folder(4, "A");
        arrayTestFolders[3] = new Folder(5, "D");

        for (Folder toAdd : arrayTestFolders)
            list.add(toAdd);
    }
    @Test
    void testRemoveFolder() {
        int initialSize = list.size();

        list.remove(arrayTestFolders[0]);
        list.remove(arrayTestFolders[0]);

        list.remove(0);
        assertEquals(list.size(), initialSize - 2);
    }

    @Test
    void testToArray() {
        ArrayList<Folder> arrayFolders = (ArrayList<Folder>) list.asList();
        assertTrue(Arrays.deepEquals(arrayFolders.toArray(), arrayTestFolders));
    }
}