package com.example.lab23a;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.lab23a.model.WriteModeTermsContainer;

public class WriteResultsController {
	
	@FXML
    private Label correctAnswerLabel;

    @FXML
    private Label progressLabel;
    
    @FXML
    private Button backBtn;

    @FXML
    private Button continueBtn;
	
	private WriteModeController parent;
	private WriteModeTermsContainer learnMode;
	private Stage stage;
	private boolean isProperExit = false;

	/**
	 *
	 * @param from - caller of the pop-up
	 * @param mode - user's progress
	 */
	public void loadData(WriteModeController from, WriteModeTermsContainer mode) {
		this.parent = from;
		this.learnMode = mode;
		
		this.correctAnswerLabel.setText("Correct answers: " + learnMode.getCurrentCorrectString());
		this.progressLabel.setText("Total progress: " + learnMode.getTotalProgressString());
		
		stage = (Stage) progressLabel.getScene().getWindow();
		stage.setOnCloseRequest(e -> {
			if(!isProperExit) {
				onContinue();
			}
		});
	}

	/**
	 * Restarts study progress if got 100%, loads unlearned terms otherwise
	 */
	public void onContinue() {
		isProperExit = true;
		if (learnMode.isFullStudied())
			parent.restart();
		else
			parent.continueLearning();
		close();
	}

	/**
	 * closes Pop-Up window and returns user to Set info
	 */
	public void onBack() {
		isProperExit = true;
		parent.goBack();
		close();
	}
	private void close() {
		 stage.close();
	}
	
	
	
}
