package com.example.lab23a.model;

/**
 * Class used for transferring information from user data to file builder.
 */
public class UserLearnStyle {

	public UserOptions getOptions() {
		return options;
	}

	private final UserOptions options;

	public UserStreak getStreak() {
		return streak;
	}

	private final UserStreak streak;

	public UserSavedInfo getInfo() {
		return info;
	}


	private final UserSavedInfo info;
	public UserLearnStyle(UserOptions userOptions, UserStreak userStreak, UserSavedInfo userInfo) {
		this.info = userInfo;
		this.streak = userStreak;
		this.options = userOptions;
	}
}
