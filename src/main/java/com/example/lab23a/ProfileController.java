package com.example.lab23a;

import com.example.lab23a.model.FileBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * Controller for profile page
 */
public class ProfileController extends ActivePaneController {

	@FXML
	private CheckBox autosaveCheckBox;

	@FXML
	private CheckBox shuffleOnCheckBox;
	@FXML
    private Label streakText;
	@Override
	public void initContent() {
		autosaveCheckBox.setSelected(getParent().getUserData().getUserOptions().getAutosaveOn());
		shuffleOnCheckBox.setSelected(getParent().getUserData().getUserOptions().getIsShuffleOn());
		streakText.setText(getParent().getUserData().getUserStreak().getStreakText());
	}
	@Override
	public void onClose() {
		getParent().getUserData().getUserOptions().setAutoSaveOn(autosaveCheckBox.isSelected());
		getParent().getUserData().getUserOptions().setShuffleOn(shuffleOnCheckBox.isSelected());
		getParent().getUserData().autoSave();
	}
	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("Profile");
	}
	

}
