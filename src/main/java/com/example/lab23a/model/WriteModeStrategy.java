package com.example.lab23a.model;

public class WriteModeStrategy {

    protected StudyTermList initialStudyTerms;

    protected StudyTermList remainingTerms;
    public WriteModeStrategy(StudyTermList list) {
        this.initialStudyTerms = list;
        update();
    }
    public void update() {
        this.remainingTerms = new StudyTermList(this.initialStudyTerms);
    }
    public StudyTerm getNextTerms() {
        return remainingTerms.pop();
    }
}
