package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TermList implements Iterable<StudyTerm> {
    protected ArrayList<StudyTerm> terms;

    public TermList() {
        terms = new ArrayList<>();
    }

    public TermList(TermList copyOf) {
        this.terms = new ArrayList<>(copyOf.terms);
    }

    public TermList(StudyTerm[] terms) {
        this.terms = new ArrayList<>(List.of(terms));
    }

    public TermList add(StudyTerm term) {
        terms.add(term);
        return this;
    }

    public StudyTerm getByIndex(int index) {
       return terms.get(index);
    }

    public void remove (StudyTerm term) {
        terms.remove(term);
    }
    public int size() {
        return terms.size();
    }


    public void refresh() {
        for (StudyTerm term : terms) term.removeProgress();
    }
    
    public ArrayList<StudyTerm> asArrayList() {
    	return this.terms;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (StudyTerm term : terms) {
            builder.append(term);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof TermList set))
            return false;

        return set.terms.equals(this.terms);
    }
    
    public int calculateMasteredCount() {
		int result = 0;
		for (StudyTerm t : terms) {
			if (t.isFullStudied())
				result++;
		}
		return result;
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

	public void insert(StudyTerm toInsert) {
		if (toInsert.getIndex() < 0 || toInsert.getIndex() >= this.size()) {
			toInsert.setIndex(this.size());
			this.terms.add(toInsert);
		}
		else
			this.terms.set(toInsert.getIndex(), toInsert);
	}



}

