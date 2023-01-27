package com.example.lab23a.model;

/**
 * StudyTerm is a pair of term and its definition. The object stores information about the pair and user's progress.
 */
public class StudyTerm {
    private String term;
    private String definition;
    private StudyProgress progress;
    private int index;

    /**
     *
     * @return order of the term in the study set
     */
    public int getIndex() {
    	return index;
    }

    /**
     *
     * @param index is the order of term in the study set
     */
    public void setIndex(int index) {
    	this.index = index;
    }

    /**
     *
     * @param index is the order of the pair in study set
     * The term and definition will be generated as empty with no progress made.
     */
    public StudyTerm(int index) {
    	this.index = index;
        this.term = "";
        this.definition = "";
        this.progress = StudyProgress.NOT_LEARNED;
    }

    /**
     *
     * @param index is the order of the pair in study set
     * @param term
     * @param definition
     */
    public StudyTerm(int index, String term, String definition) {
    	this.index = index;
        this.term = term;
        this.definition = definition;
        this.progress = StudyProgress.NOT_LEARNED;
    }

    /**
     *
     * @param index - the order of the pair in Study Set
     * @param term -
     * @param definition -
     * @param progress - study progress, made by user
     */
    public StudyTerm(int index, String term, String definition, StudyProgress progress) {
        this(index, term, definition);
        this.progress = progress;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    /**
     *
     * @return definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     *
     * @param definition is the second string in pair, which will be shown to user in Write mode in order to determine the term
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     *
     * @return progress that user made in studying the term
     */
    public StudyProgress getProgress() {
        return progress;
    }

    /**
     *
     * @param progress is the value of knowing the term by user, starting from not-learned to mastered
     */
    public void setProgress(StudyProgress progress) {
        this.progress = progress;
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

    /**
     *
     * @return true if the term is mastered, meaning the user has got it first-try in write-mode last time
     */
    public boolean isFullStudied() {
    	return this.progress == StudyProgress.MASTERED;
    }


    /**
     * Resets study progress to 'Not learned'
     */
	public void removeProgress() {
		setProgress(StudyProgress.NOT_LEARNED);
	}
}

