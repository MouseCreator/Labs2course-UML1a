package com.example.lab23a.model;

public class WriteModeStrategy {

    protected TermList initialStudyTerms;

    protected StudyTermList remainingTerms;
    public WriteModeStrategy(TermList list) {
        this.initialStudyTerms = list;
        update(list);
    }
    public void update(TermList list) {
        this.initialStudyTerms = list;
        this.remainingTerms = new StudyTermList(initialStudyTerms);
    }
    public StudyTerm getNextTerms() {
        return remainingTerms.pop();
    }
}
