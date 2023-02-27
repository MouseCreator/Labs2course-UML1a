package com.example.lab23a.model;

import java.util.ArrayList;
public class StudyTermList extends AbstractTermList{
    public StudyTermList(TermList origin) {
        super(origin);
    }
    public StudyTermList(StudyTermList origin) {
        this.terms = new ArrayList<>(origin.terms);
    }
    public StudyTermList() {
        super();
    }

    public StudyTermList(ArrayList<StudyTerm> toShuffle) {
        this.terms = new ArrayList<>(toShuffle);
    }

    /**
     *
     * @return first element of the list
     * @throws IndexOutOfBoundsException if list is empty
     */
    public StudyTerm pop() throws IndexOutOfBoundsException{
    	return terms.remove(0);
    }

    /**
     * Removes all sets from the list
     */
    public void clear() {
    	 terms.clear();
    }
	
}
