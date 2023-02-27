package com.example.lab23a.model;

public class WriteModeShuffleStrategy extends WriteModeStrategy{
    public WriteModeShuffleStrategy(TermList list) {
        super(list);
    }
    @Override
    public void update(TermList list) {
        super.update(list);
        remainingTerms.shuffle();
    }
}
