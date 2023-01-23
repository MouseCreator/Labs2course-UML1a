package com.example.lab23a;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.example.lab23a.model.TermWriteMode;

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
	private TermWriteMode learnMode;
	private Stage stage;
	private boolean isProperExit = false;
	
	public void loadData(WriteModeController from, TermWriteMode mode) {
		this.parent = from;
		this.learnMode = mode;
		
		this.correctAnswerLabel.setText("Correct answers: " + learnMode.getCurrentCorrectString());
		this.progressLabel.setText("Total progress: " + learnMode.getTotalProgressString());
		
		stage = (Stage) progressLabel.getScene().getWindow();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	if(!isProperExit) {
		    		onContrinue();
		    	}
		    }
		  });
	}
	
	public void onContrinue() {
		isProperExit = true;
		if (learnMode.isFullStudied())
			parent.restart();
		else
			parent.continueLearning();
		close();
	}
	

	public void onBack() {
		isProperExit = true;
		parent.goBack();
		close();
	}
	private void close() {
		 stage.close();
	}
	
	
	
}
