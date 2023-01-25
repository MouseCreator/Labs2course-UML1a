package com.example.lab23a.model;

public class StudyTerm {
    private String term;
    private String definition;

    private StudyProgress progress;
    
    private String progressString;
    
    private int index;
    
    public int getIndex() {
    	return index;
    }
    public void setIndex(int index) {
    	this.index = index;
    }
    
    public String getProgressString() {
    	return progressString;
    }

    public StudyTerm(int index) {
    	this.index = index;
        this.term = "";
        this.definition = "";
        this.progress = StudyProgress.NOT_LEARNED;
        updateProgressString();
    }
    public StudyTerm(int index, String term, String definition) {
    	this.index = index;
        this.term = term;
        this.definition = definition;
        this.progress = StudyProgress.NOT_LEARNED;
        updateProgressString();
    }
    public StudyTerm(int index, String term, String definition, StudyProgress progress) {
        this(index, term, definition);
        this.progress = progress;
        updateProgressString();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public StudyProgress getProgress() {
        return progress;
    }
    public void setProgress(StudyProgress progress) {
        this.progress = progress;
        updateProgressString();
    }

    @Override
    public String toString() {
        return "[" + term + " " +
                definition + " " +
                progress + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof StudyTerm otherTerm))
            return false;

        return otherTerm.definition.equals(this.definition) && otherTerm.term.equals(this.term) && this.progress == otherTerm.progress;
    }
    
    public boolean isFullStudied() {
    	return this.progress == StudyProgress.MASTERED;
    }
    
    private void updateProgressString() {
        switch (progress) {
            case NOT_LEARNED -> this.progressString = "Not learned";
            case FAMILIAR -> this.progressString = "Familiar";
            case LEARNED -> this.progressString = "Learned";
            case MASTERED -> this.progressString = "Mastered";
            default -> System.err.println("Error casting study progress to string");
        }
    }
	public void removeProgress() {
		setProgress(StudyProgress.NOT_LEARNED);
		updateProgressString();
	}
}

