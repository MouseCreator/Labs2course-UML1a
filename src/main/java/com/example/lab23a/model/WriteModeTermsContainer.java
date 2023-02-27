package com.example.lab23a.model;

/**
 * Class that contains study terms that user learns during a period and additional data, including attempts and progress.
 */
public class WriteModeTermsContainer {
	private final TermList studyResult;
	private final TermList origin;
	private final TermList toNextPeriod;

	private WriteModeStrategy termPickStrategy;
	private int[] attempts;
	private int currentRest;
	private int currentAnswered;
	private int rightAnswered;
	private int currentCorrect;

	public WriteModeTermsContainer(TermList origin, UserLearnStyle style) {
		this.origin = origin;
		currentRest = origin.size();
		currentAnswered = 0;
		rightAnswered = 0;
		this.studyResult = new TermList(origin);
		studyResult.refresh();
		toNextPeriod = new TermList();
		initAttempts();
		initStrategy(origin, style);
	}

	private void initStrategy(TermList from, UserLearnStyle style) {
		if (style.getIsShuffleOn())
			this.termPickStrategy = new WriteModeShuffleStrategy(from);
		else
			this.termPickStrategy = new WriteModeStrategy(from);
	}
	/**
	 * Has to be called between two study sessions (periods) of one study set.
	 * This method refreshes list of terms that has to be studied next time.
	 */
	public void reinit() {
		termPickStrategy.update(toNextPeriod);
		currentRest = toNextPeriod.size();
		currentAnswered = 0;
		currentCorrect = 0;
		toNextPeriod.clear();
	}
	
	private void initAttempts() {
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
		return termPickStrategy.getNextTerms();
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

	private StudyProgress convertIntToStudyProgress(int attempt) {
		return switch (attempt) {
			case 0 -> StudyProgress.MASTERED;
			case 1 -> StudyProgress.LEARNED;
			default -> StudyProgress.FAMILIAR;
		};
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
	/**
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
	 * @return current progress value in percent, rounded
	 */
	public String getCurrentCorrectString() {
		return Math.round(100 * getCurrentCorrect()) + "%";
	}
	/**
	 * 
	 * @return total progress as value in percent, rounded
	 */
	public String getTotalProgressString() {
		return Math.round(100 * getTotalProgress()) + "%";
	}
	
}
