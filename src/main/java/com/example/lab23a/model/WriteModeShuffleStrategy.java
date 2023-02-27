package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Collections;

public class WriteModeShuffleStrategy extends WriteModeStrategy{
    public WriteModeShuffleStrategy(TermList list) {
        super(list);
    }
    @Override
    public void update(TermList list) {
        ArrayList<StudyTerm> toShuffle = initialStudyTerms.asArrayList();
        Collections.shuffle(toShuffle);
        this.remainingTerms = new TermList(toShuffle);
    }
}
