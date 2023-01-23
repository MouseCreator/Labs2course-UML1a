package com.example.lab23a.model;

public class TermWriteMode {
	private TermList studyResult;
	private TermList origin;
	private StudyTermList studyList;
	private StudyTermList toNextPeriod;
	
	private int[] attempts;
	
	public TermWriteMode(TermList origin, UserLearnStyle style) {
		this.origin = origin;
		currentRest = origin.size();
		currentAnswered = 0;
		rightAnswered = 0;
		this.studyResult = new TermList(origin);
		studyResult.refresh();
		this.studyList = new StudyTermList(origin, style);
		toNextPeriod = new StudyTermList(style);
		initAttemps();
	}
	
	private int currentRest;
	private int currentAnswered;
	private int rightAnswered;
	private int currentCorrect;
	/**
	 * Has to be called between 2 study sessions (periods) of one study set.
	 * This method refreshes list of terms that has to be studied next time.
	 */
	public void reinit() {
		studyList = new StudyTermList(toNextPeriod);
		currentRest = studyList.size();
		currentAnswered = 0;
		currentCorrect = 0;
		toNextPeriod.clear();
		
		if (studyList.getStyle().getIsShuffleOn())
			studyList.shuffle();
	}
	
	private void initAttemps() {
		attempts = new int[origin.size()];
	}
	/**
	 * 
	 * @return list all terms in the set with updated status
	 */
	public TermList getStudyResult() {
		return studyResult;
	}
	/**
	 * 
	 * @return next term in the list for the session
	 * @throws IndexOutOfBoundsException if there are no more terms left for this session
	 */
	public StudyTerm getNextTerm() throws IndexOutOfBoundsException {
		return studyList.pop();
	}
	
	/**
	 * 
	 * @param checker decides if the answer was entered first-Try
	 * @param current is study set, whose term was entered
	 */
	public void onCorrectEntered(WriteAnswerChecker checker, StudyTerm current) {
		if (checker.getIsFirstTry())
			onCorrectAnswer(current); 
		else
			onWrongAnswer(current);
	}
	
	private void onCorrectAnswer(StudyTerm current) {
		current.setProgress(convertIntToStudyProgress(attempts[current.getIndex()]));
		studyResult.insert(current);
		rightAnswered++;
		currentAnswered++;
		currentCorrect++;
	}
	
	private void onWrongAnswer(StudyTerm current) {
		attempts[current.getIndex()]++;
		toNextPeriod.add(current);
		currentAnswered++;
	}
	/**
	 * 
	 * @param term that will be changed: its term field will be assigned to <changed>
	 */
	public void onChangedAnswer(StudyTerm term, String changed) {
		term.setTerm(changed);
		onCorrectAnswer(term);
	}
	
	private StudyProgress convertIntToStudyProgress(int value) {
		switch(value) {
		case 0: return StudyProgress.MASTERED;
		case 1: return StudyProgress.LEARNED;
		default: return StudyProgress.FAMILIAR;
		}
	}
	/**
	 * 
	 * @return true if all there are no not studied terms left in the period
	 */
	public boolean isPeriodEnd() {
		return currentAnswered == currentRest;
	}
	/**
	 * 
	 * @return true if user studied all terms
	 */
	public boolean isFullStudied() {
		return rightAnswered == origin.size();
	}
	/**
	 * 
	 * @return progress in percent for current period
	 */
	public double getCurrentProgress() {
		return ((double) currentAnswered) / currentRest;
	}
	/*
	 * @return progress in percent for current period, including only terms that was right answered
	 */
	public double getCurrentCorrect() {
		return ((double) currentCorrect) / currentAnswered;
	}
	/**
	 * 
	 * @return progress the number of studied terms with respect to the number of all terms
	 */
	public double getTotalProgress() {
		return ((double)rightAnswered) / origin.size();
	}
	/**
	 * 
	 * @return current progress with "%" symbol
	 */
	public String getCurrentCorrectString() {
		return Math.round(100 * getCurrentCorrect()) + "%";
	}
	/**
	 * 
	 * @return total progress with "%" symbol
	 */
	public String getTotalProgressString() {
		return Math.round(100 * getTotalProgress()) + "%";
	}
	
}
