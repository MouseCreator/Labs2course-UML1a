package com.example.lab23a.model;

public class WriteModeStrategy {

    protected StudyTermList initialStudyTerms;

    protected StudyTermList remainingTerms;
    public WriteModeStrategy() {
    }
    public void setTerms(StudyTermList list) {
        this.initialStudyTerms = list;
        update(list);
    }
    public void update(StudyTermList list) {
        this.initialStudyTerms = list;
        this.remainingTerms = new StudyTermList(initialStudyTerms);
    }
    public StudyTerm getNextTerms() {
        return remainingTerms.pop();
    }

    public boolean isApplicable(UserOptions options) {
        return !options.getIsShuffleOn();
    }
}
