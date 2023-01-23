package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Collections;

public class StudyTermList extends TermList{

    private UserLearnStyle style;

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
	}
	public UserLearnStyle getStyle() {
        return style;
    }

    public void setStyle(UserLearnStyle style) {
        this.style = style;
    }
    

    public void shuffle() {
        Collections.shuffle(this.terms);
    }
    public StudyTerm pop() throws IndexOutOfBoundsException{
    	return terms.remove(0);
    }
    public boolean isEmpty() {
    	return terms.isEmpty();
    }
    public void clear() {
    	 terms.clear();
    }
	
}
