package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class TermList implements Iterable<StudyTerm> {
    protected ArrayList<StudyTerm> terms;

    public TermList() {
        terms = new ArrayList<>();
    }

    public TermList(ArrayList<StudyTerm> terms) {
        this.terms = terms;
    }
    public TermList(TermList copyOf) {
        this.terms = new ArrayList<>(copyOf.terms);
    }

    public TermList(StudyTerm[] terms) {
        this.terms = new ArrayList<StudyTerm>(List.of(terms));
    }

    public TermList add(StudyTerm term) {
        terms.add(term);
        return this;
    }

    public StudyTerm getByIndex(int index) {
       return terms.get(index);
    }
    public StudyTerm getByTerm(String term) {
        for (StudyTerm studyTerm : terms) {
            if (studyTerm.getTerm().equals(term))
                return studyTerm;
        }
        throw new NoSuchElementException();
    }
    public StudyTerm getByDefinition(String definition) {
        for (StudyTerm studyTerm : terms) {
            if (studyTerm.getDefinition().equals(definition))
                return studyTerm;
        }
        throw new NoSuchElementException();
    }

    public void remove (StudyTerm term) {
        terms.remove(term);
    }

    public int size() {
        return terms.size();
    }


    public void refresh() {
        for (int i = 0; i < terms.size(); i++) 
            terms.get(i).removeProgress();
        
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

        if (!(other instanceof TermList))
            return false;

        TermList set = (TermList) other;

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
        Iterator<StudyTerm> iterator = new Iterator<StudyTerm>() {

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
        return iterator;
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

