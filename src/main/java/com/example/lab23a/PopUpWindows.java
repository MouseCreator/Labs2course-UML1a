package com.example.lab23a;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.lab23a.model.FileBuilder;

public class PopUpWindows {
	private PopupController controller;
	
	
	public PopUpWindows() {
		
	}
	public void showDeletionConfirm(Window owner, Runnable onConfirm) {
		PopUpTemplateWindow window = new PopUpTemplateWindow("Confirm deletion", "Are you sure you want to delete this study set?");
		window.setOnConfirm(onConfirm);
		fromWindow(owner, window);
	}
	public void showUnsavedChangesConfirm(Window owner, Runnable onConfirm, Runnable onDecline) {
		PopUpTemplateWindow window = new PopUpTemplateWindow("Unsaved changes", "You have some unsaved changes. Do you want to save them?");
		window.setOnConfirm(onConfirm);
		window.setOnDecline(onDecline);
		fromWindow(owner, window);
	}
	private void fromWindow(Window owner, PopUpTemplateWindow template) {
		final Stage dialog = initDialog(owner, template.getTitle());
		controller = initScene(dialog);
		initController(template);
	    dialog.show();
	}
	
	
	private void initController(String message) {
		controller.setMessage(message);
	}
	private void initController(PopUpTemplateWindow tempate) {
		initController(tempate.getMessage());
		controller.setOnAccept(tempate.getOnConfirm());
		controller.setOnDecline(tempate.getOnDecline());
	}
	private Stage initDialog(Window owner, String title) {
		Stage dialog = new Stage();
		dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle(title);
        dialog.setResizable(false);
        IconLoader.addAttentionIcon(dialog);
        return dialog;
	}
	private PopupController initScene(Stage dialog) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("WarningDialog")));
        try {
        	Parent root = fxmlLoader.load();
      		Scene dialogScene = new Scene(root, 256, 128);
			dialogScene.setRoot(root);
			dialog.setScene(dialogScene);
        } catch (IOException e) {
			e.printStackTrace();
        }
        return fxmlLoader.getController();
	}
	public void showFolderDeletionConfirm(Window owner, Runnable confirm) {
		PopUpTemplateWindow window = new PopUpTemplateWindow("Confirm deletion", "Are you sure you want to delete this folder?");
		window.setOnConfirm(confirm);
		fromWindow(owner, window);
	}
	public void showExitStudyConfirm(Window owner, Runnable accept) {
		PopUpTemplateWindow window = new PopUpTemplateWindow("Confirm exit", "Are you sure you want to leave learn mode? Your progress might be lost.");
		window.setOnConfirm(accept);
		fromWindow(owner, window);
		
	}
}
