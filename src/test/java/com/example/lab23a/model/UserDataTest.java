package com.example.lab23a.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {
    private UserData data;
    @BeforeEach
    public void initializeData() {
        data = new UserData();
    }
    @Test
    void insertNewSet() {
        data.insertNewSet(new SetIndex(1));
        data.insertNewSet(new SetIndex(2));
        assertEquals(data.getIndexList().size(),2);
        SetIndex generated = data.generateNewIndex();
        assertEquals(generated.getID(), 3);
    }


    @Test
    void appendNewFolder() {
        data.appendNewFolder("Folder");
        assertEquals(data.getFolderList().size(), 1);
        assertEquals(data.getFolderList().get(0).getName(), "Folder");
    }
}