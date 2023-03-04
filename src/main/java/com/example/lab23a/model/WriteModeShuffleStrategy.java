package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Collections;

public class WriteModeShuffleStrategy extends WriteModeStrategy{
    @Override
    public void update(StudyTermList list) {
        ArrayList<StudyTerm> toShuffle = initialStudyTerms.asArrayList();
        Collections.shuffle(toShuffle);
        this.remainingTerms = new StudyTermList(toShuffle);
    }
    @Override
    public boolean isApplicable(UserOptions options) {
        return options.getIsShuffleOn();
    }
}
