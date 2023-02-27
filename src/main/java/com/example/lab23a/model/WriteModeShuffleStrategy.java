package com.example.lab23a.model;

public class WriteModeShuffleStrategy extends WriteModeStrategy{
    public WriteModeShuffleStrategy(StudyTermList list) {
        super(list);
    }
    @Override
    public void update() {
        this.remainingTerms = new StudyTermList(this.initialStudyTerms);
        remainingTerms.shuffle();
    }
}
