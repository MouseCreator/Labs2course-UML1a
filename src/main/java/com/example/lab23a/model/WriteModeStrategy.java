package com.example.lab23a.model;

public class WriteModeStrategy {

    protected TermList initialStudyTerms;

    protected TermList remainingTerms;
    public WriteModeStrategy(TermList list) {
        this.initialStudyTerms = list;
        update(list);
    }
    public void update(TermList list) {
        this.initialStudyTerms = list;
        this.remainingTerms = new TermList(initialStudyTerms);
    }
    public StudyTerm getNextTerms() {
        return remainingTerms.pop();
    }
}
