package com.example.lab23a;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class ProfileController extends ActivePaneController {
	
	@FXML
	private CheckBox autosaveCheckBox;

	@FXML
	private CheckBox shuffleOnCheckBox;
	@FXML
    private Label streakText;
	@Override
	public void initContent() {
		autosaveCheckBox.setSelected(getParent().getUserData().getStyle().getAutosaveOn());
		shuffleOnCheckBox.setSelected(getParent().getUserData().getStyle().getIsShuffleOn());
		streakText.setText(getParent().getUserData().getStyle().getStreakText());
	}
	@Override
	public void onClose() {
		getParent().getUserData().getStyle().setAutoSaveOn(autosaveCheckBox.isSelected());
		getParent().getUserData().getStyle().setShuffleOn(shuffleOnCheckBox.isSelected());
		getParent().getUserData().autoSave();
	}
	

}
