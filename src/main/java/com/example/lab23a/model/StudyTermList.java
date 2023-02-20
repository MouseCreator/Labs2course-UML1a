package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Collections;

public class StudyTermList extends TermList{
    private final UserLearnStyle style;

    public StudyTermList(TermList origin, UserLearnStyle style) {
        super(origin);
        this.style = style;
        if (style.getIsShuffleOn()) {
        	this.shuffle();
        }
    }
    public StudyTermList(UserLearnStyle style) {
        super();
        this.style = style;
        if (style.getIsShuffleOn()) {
        	this.shuffle();
        }
    }

    public StudyTermList(StudyTermList other) {
		this.terms = new ArrayList<>(other.terms);
        this.style = other.style;
	}
	public UserLearnStyle getStyle() {
        return style;
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

    /**
     * Removes all sets from the list
     */
    public void clear() {
    	 terms.clear();
    }
	
}
