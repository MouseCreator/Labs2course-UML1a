package com.example.lab23a.Server;

import com.example.lab23a.model.Dates;
import com.example.lab23a.model.SetIndex;

public class SetIndexParsable {
    public SetIndexParsable(int index) {
        this.index = index;
    }

    private int index;
    private String name;
    private int elementCount;
    private int masteredCount;
    private String created;
    private String updated;
    public SetIndexParsable(SetIndex origin) {
       this.index = origin.getID();
       this.name = origin.getName();
       this.elementCount = origin.getElementsCount();
       this.masteredCount = origin.getElementsMastered();
       this.created = Dates.toDateFormat(origin.getCreatedDate());
       this.updated = Dates.toDateFormat(origin.getLastStudied());
    }

    public SetIndexParsable(int index, String name, int elementCount, int elementMastered, String date1, String date2) {
        this.index = index;
        this.name = name;
        this.elementCount = elementCount;
        this.masteredCount =elementMastered;
        this.created = date1;
        this.updated = date2;
    }

    public SetIndex toIndex() {
        return new SetIndex(index, name, elementCount, masteredCount, Dates.fromDateFormat(created), Dates.fromDateFormat(updated));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElementCount() {
        return elementCount;
    }

    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }

    public int getMasteredCount() {
        return masteredCount;
    }

    public void setMasteredCount(int masteredCount) {
        this.masteredCount = masteredCount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
