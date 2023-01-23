package com.example.lab23a.model;

import java.time.LocalDate;

public class UserLearnStyle {
	
	private boolean isShuffleOn;
	private boolean autoSaveOn;
	private int lastUsedIndex;
	private int lastUsedIndexFolder;
	private LocalDate lastStudiedDate;
	private int userStreakInDays;
	
	public UserLearnStyle() {
		defaultSettings();
	}
	public void defaultSettings() {
		isShuffleOn = true;
	}
	
	public LocalDate getLastStudied() {
		return lastStudiedDate;
	}
	public void setLastStudied(LocalDate date) {
		lastStudiedDate = date;
	}
	public void setShuffleOn(boolean isShuffleOn) {
		this.isShuffleOn = isShuffleOn;
	}
	public void setAutoSaveOn(boolean value) {
		this.autoSaveOn = value;
	}
	public boolean getIsShuffleOn() {
		return this.isShuffleOn;
	}
	public boolean getAutosaveOn() {
		return this.autoSaveOn;
	}
	public int getLastUsedIndex() {
		return lastUsedIndex;
	}
	public void setLastUsedIndex(int lastUsedIndex) {
		this.lastUsedIndex = lastUsedIndex;
	}
	public int getLastUsedIndexFolder() {
		return lastUsedIndexFolder;
	}
	public void setLastUsedIndexFodler(int lastUsedIndexFolder) {
		this.lastUsedIndexFolder = lastUsedIndexFolder;
	}
	
	private void updateLastStudiedDate() {
		this.lastStudiedDate = Dates.currentDate().toLocalDate();
	}
	/**
	 * Sets streak to 0 if user skipped a day of studying 
	 */
	public void initStreak() {
		if (lastStudiedDate == null) {
			lastStudiedDate = Dates.currentDate().toLocalDate();
			this.userStreakInDays = 0;
		}
		else if (Dates.compareDaysPassed(Dates.currentDate().toLocalDate(), lastStudiedDate) > 1) {
			this.userStreakInDays = 0;
		}
	}
	/**
	 * Increments user's streak if it hasn't been done today
	 */
	public void updateStreak() {
		if (Dates.compareDaysPassed(Dates.currentDate().toLocalDate(), lastStudiedDate) == 1 || userStreakInDays == 0) {
			updateLastStudiedDate();
			userStreakInDays++;
		}
	}
	/**
	 * 
	 * @return number of days that user has studied in a row
	 */
	public int getStreak() {
		return userStreakInDays;
	}
	public void setStreak(int v) {
		this.userStreakInDays = v;
	}
	/**
	 * 
	 * @return empty string if user doesn't have a streak or a message that states how many days in a row user have
	 */
	public String getStreakText() {
		if (userStreakInDays == 0)
			return "";
		else 
			return "You are on a " + userStreakInDays + "-day streak!";
	}
	private String lastSavedFileAbsolutePath = "C:\\";
	public String getLastSavedFileAbsolutePath() {
		return lastSavedFileAbsolutePath;
	}

	public void updateLastSavedFileAbsolutePath(String newPath) {
		this.lastSavedFileAbsolutePath = newPath;
	}
}
