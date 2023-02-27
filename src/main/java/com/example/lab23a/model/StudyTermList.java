package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Collections;

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

    /**
     * Randomizes the order of elements in the list
     */
    public void shuffle() {
        Collections.shuffle(this.terms);
    }

    /**
     *
     * @return first element of the list
     * @throws IndexOutOfBoundsException if list is empty
     */
    public StudyTerm pop() throws IndexOutOfBoundsException{
    	return terms.remove(0);
    }


	
}
