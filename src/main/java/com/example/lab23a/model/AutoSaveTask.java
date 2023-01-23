package com.example.lab23a.model;

import javafx.concurrent.Task;

public class AutoSaveTask extends Task<UserData> {
	private UserData data;
	
	public AutoSaveTask(UserData from) {
		this.data = from;
	}
	
	@Override
	protected UserData call() throws Exception {
		data.saveData();
		return data;
	}
}
