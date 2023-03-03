package com.example.lab23a;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class for pop-up windows with two options
 */
public class PopupController {
	@FXML
    private Text messageLabel;
    /**
     * Action to do when user accepted the proposition
     */
    private Runnable acceptCommand;
    /**
     * Action to do when user declined the proposition
     */
    private Runnable declineCommand;

    /**
     *
     * @param message - text to display, when the pop-up is shown
     */
    public void setMessage(String message) {
    	this.messageLabel.setText(message);
    }

    /**
     *
     * @param method - action to do, when user accepted the proposition
     */
    public void setOnAccept(Runnable method) {
    	this.acceptCommand = method;
    }
    /**
     *
     * @param method - action to do, when user declined the proposition
     */
    public void setOnDecline(Runnable method) {
    	this.declineCommand = method;
    }
    /**
     * Perform the accept action
     */
    public void onAccepted() {
    	acceptCommand.run();
    	close();
    }
    /**
     * Perform the decline action
     */
    public void onDecline() {
    	declineCommand.run();
    	close();
    }
    
    private void close() {
    	((Stage)this.messageLabel.getScene().getWindow()).close();
    }
}
