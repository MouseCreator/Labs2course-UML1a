package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractTermList implements Iterable<StudyTerm>{
    protected ArrayList<StudyTerm> terms;

    public AbstractTermList() {
        terms = new ArrayList<>();
    }

    public AbstractTermList(TermList copyOf) {
        this.terms = new ArrayList<>(copyOf.terms);
    }

    @Override
    public Iterator<StudyTerm> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public StudyTerm next() {
                return terms.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    public int size() {
        return terms.size();
    }

    public void add(StudyTerm term) {
        terms.add(term);
    }
    public ArrayList<StudyTerm> asArrayList() {
        return this.terms;
    }
    public StudyTerm getByIndex(int index) {
        return terms.get(index);
    }

}
