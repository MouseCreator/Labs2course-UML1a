package com.example.lab23a;

import com.example.lab23a.model.FileBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.lab23a.model.WriteModeTermsContainer;

/**
 * Controls the additional window that displays results of the write mode.
 */
public class WriteResultsController extends AdditionalWindowController{
	
	@FXML
    private Label correctAnswerLabel;

    @FXML
    private Label progressLabel;
    
    @FXML
    private Button backBtn;

    @FXML
    private Button continueBtn;
	private WriteModeTermsContainer learnMode;

	private WriteModeController calledBy;
	private boolean isProperExit = false;

	/**
	 *
	 * @param mode - user's progress
	 */
	public void loadData(WriteModeTermsContainer mode) {
		this.learnMode = mode;
		
		this.correctAnswerLabel.setText("Correct answers: " + learnMode.getCurrentCorrectString());
		this.progressLabel.setText("Total progress: " + learnMode.getTotalProgressString());

	}

	/**
	 * Restarts study progress if got 100%, loads unlearned terms otherwise
	 */
	public void onContinue() {
		isProperExit = true;
		if (learnMode.isFullStudied())
			calledBy.restart();
		else
			calledBy.continueLearning();
		close();
	}



	/**
	 * closes Pop-Up window and returns user to Set info
	 */
	public void onBack() {
		isProperExit = true;
		calledBy.goBack();
		close();
	}
	private void close() {
		 stage.close();
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnCloseRequest(e -> {
			if(!isProperExit) {
				onContinue();
			}
		});
	}
	@Override
	public String getFilename() {
		return FileBuilder.FXMLDestination("WritePartResults");
	}
	public WriteResultsController load(WriteModeController calledBy, WriteModeTermsContainer data) {
		WriteResultsController controller = (WriteResultsController) super.load(calledBy.getParent());
		controller.calledBy = calledBy;
		controller.setHeight(480);
		controller.setHeight(720);
		controller.loadData(data);
		return controller;
	}
	@Override
	public String getTitle() {
		return "Your results";
	}
}
