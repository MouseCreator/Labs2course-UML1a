package com.example.lab23a.model;

public class StudySet {


    private SetIndex index;
    private TermList list;

    public StudySet(SetIndex index, TermList list) {
        this.list = list;
        this.index = index;
    }
    
    public StudySet(SetIndex index) {
        this.list = new TermList();
        this.index = index;
    }
    
    public TermList getList() {
        return list;
    }

    public void setList(TermList list) {
        this.list = list;
    }

    public SetIndex getIndex() {
        return index;
    }

    public StudySet(int id) {
        open(id);
    }

    public void open(int id) {
        this.list = FileBuilder.readTerms(id);
    }

    public void setIndex(SetIndex index) {
        this.index = index;
    }


}
