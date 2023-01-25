package com.example.lab23a.model;

import javafx.concurrent.Task;

public class AutoSaveTask extends Task<UserData> {
	private final UserData data;
	
	public AutoSaveTask(UserData from) {
		this.data = from;
	}
	
	@Override
	protected UserData call() {
		data.saveData();
		return data;
	}
}
