package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.List;

public class TermList extends AbstractTermList {

    public TermList() {
        terms = new ArrayList<>();
    }

    public TermList(TermList copyOf) {
        this.terms = new ArrayList<>(copyOf.terms);
    }

    public TermList(StudyTerm[] terms) {
        this.terms = new ArrayList<>(List.of(terms));
    }

    /**
     * Removes progress from all study terms in the list
     */
    public void refresh() {
        for (StudyTerm term : terms) term.removeProgress();
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

    /**
     *
     * @return number of mastered study terms
     */
    public int calculateMasteredCount() {
		int result = 0;
		for (StudyTerm t : terms) {
			if (t.isFullStudied())
				result++;
		}
		return result;
	}

    /**
     * Adds study term, if it is not added yet
     * @param toInsert - term to add
     */
	public void insert(StudyTerm toInsert) {
		if (toInsert.getIndex() < 0 || toInsert.getIndex() >= this.size()) {
			toInsert.setIndex(this.size());
			this.terms.add(toInsert);
		}
		else
			this.terms.set(toInsert.getIndex(), toInsert);
	}





}

