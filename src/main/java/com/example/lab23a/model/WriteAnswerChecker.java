package com.example.lab23a.model;

import java.util.Random;

public class WriteAnswerChecker {
	
	private boolean firstTry;
	private String term;
	
	private Random random;
	
	public WriteAnswerChecker() {
		firstTry = true;
		random = new Random();
	}
	public void setTerm(String term) {
		this.term = term;
		firstTry = true;
	}
	/**
	 * 
	 * @param userAnswer is answer that should be compared to correct answer
	 * @return true if the answer is correct (ignoring case and extra spaces)
	 */
	public boolean checkAnswer(final String userAnswer) {
		String expected = term;
		String actual = userAnswer;
		boolean isCorrect = compare(expected, actual);
		if (isCorrect) {
			return true;
		}
		else {
			firstTry = false;
			return false;
		}
	}
	private boolean compare(String expected, String actual) {
		return expected.trim().equalsIgnoreCase(actual.trim());
	}
	/**
	 * 
	 * @return true if user typed correct answer and did not typed incorrect previously
	 */
	public boolean getIsFirstTry() {
		return this.firstTry;
	}
	/**
	 * 
	 * @param actual is user's previous answer
	 * @return message that shows the difference between user's and correct answers
	 */
	public String getWrongAnswerString(String actual) {
		try {
			return "Expected: " + term + "\nActual: " + actual;
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
		return "Wrong answer!";
	}
	/**
	 * 
	 * @return motivation message for user such as "Correct!", "Good job!", etc.
	 */
	public String getRandomCorrentAnswerString() {
		final String[] options = {"Good job!", "Correct!", "Onward!"};
		return options[random.nextInt(options.length)];
		
	}
	/**
	 * changes user's answer to correct (means that attempt was successful)
	 */
	public void changeToRightAnswer() {
		this.firstTry = true;
	}
	
	
}
