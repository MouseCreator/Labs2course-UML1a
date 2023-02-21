package com.example.lab23a;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.Folder;
import com.example.lab23a.model.UserData;

public class WorkspaceController implements Initializable {

    @FXML
    private Pane menuBarPane;

    @FXML
    private Pane workspacePane;

    
    private final UserData userData = new UserData();

    private PopUpWindows confirmWindow;

    
    ActivePaneController currentActive;
    
    private Folder lastActiveFolder = new Folder(Folder.ALL_SETS);
    
    private Runnable proceedClosing = ()->{};
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadUserData();
		loadGUIObjects();
		loadPage(new SetOpenController().load(this, new Folder(Folder.ALL_SETS)));
		initDeletionPopup();
		Platform.runLater(()->
				workspacePane.getScene().getWindow().setOnCloseRequest(t -> {
					userData.saveData();
					currentActive.onClose();
				}));
	}
	
	public Folder getLastActive() {
		return lastActiveFolder;
	}
	public void refreshLastActive() {
		lastActiveFolder = new Folder(Folder.ALL_SETS);
	}
	
	private void loadUserData() {
		userData.load();
	}
	
	private void loadGUIObjects() {
		loadMenu();
	}
	public UserData getUserData() {
		return userData;
	}
	
	public void loadMenu() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("MenuBar")));
			menuBarPane.getChildren().add(fxmlLoader.load());
			MenuBarController c = fxmlLoader.getController();
			c.setParent(this);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	private void loadScene() {
		workspacePane.getChildren().clear();
		workspacePane.getChildren().add(currentActive.getContent());
    }
	
	public void loadPage(ActivePaneController toLoad) {
		if (closeActiveController())
			loadNext(toLoad);
		else
			this.proceedClosing = ()->loadNext(toLoad);
		
	}
	
	private void loadNext(ActivePaneController key) {
		if (currentActive != null)
			currentActive.onClose();
		currentActive = key;
		loadScene();
		currentActive.initContent();
	}
	
	private boolean closeActiveController() {
		if (currentActive == null) { 
			return true;
		}
		return(currentActive.onCloseRequest());
	}

	public void proceedClosingOperation() {
		this.proceedClosing.run();
		this.proceedClosing = () -> {};
	}

	private void initDeletionPopup() {
		this.confirmWindow = new PopUpWindows();
	}


	public void openAdditionalWindow(AdditionalWindowController toOpen) {
		Parent root = toOpen.getContent();
		Scene additionalScene = new Scene(root, toOpen.getWidth(), toOpen.getHeight());
		additionalScene.getStylesheets().
				add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
		Stage newStage = new Stage();
		newStage.setScene(additionalScene);
		newStage.setTitle(toOpen.getTitle());
		newStage.setResizable(false);
		newStage.getIcons().add(IconLoader.load());
		toOpen.setStage(newStage);
		newStage.show();
	}

	
	public void displayDeletionPopUp(Runnable confirm) {
		this.confirmWindow.showDeletionConfirm(getWindow(), confirm);
	}
	public void displayFolderDeletionPopUp(Runnable confirm) {
		this.confirmWindow.showFolderDeletionConfirm(getWindow(), confirm);
	}
	
	public Window getWindow() {
		return this.workspacePane.getScene().getWindow();
	}

	public void displaySavingPopUp(Runnable accept, Runnable decline) {
		this.confirmWindow.showUnsavedChangesConfirm(getWindow(), accept, decline);
	}

	public void displayExitPopUp(Runnable accept) {
		this.confirmWindow.showExitStudyConfirm(getWindow(), accept);
	}

}
