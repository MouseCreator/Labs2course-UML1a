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
    
    private Stage folderStage;
    
    private PopUpWindows confirmWindow;
    
    private FolderViewController folderViewController;
    
    ActivePaneController currentActive;
    
    private Folder lastActiveFolder = new Folder(Folder.ALL_SETS);
    
    private Runnable proceedClosing = ()->{};
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadUserData();
		loadGUIObjects();
		loadPage(new SetOpenController().load(this, new Folder(Folder.ALL_SETS)));
		initFolderWindow();
		initDeletionPopup();
		Platform.runLater(()->
				workspacePane.getScene().getWindow().setOnCloseRequest(t -> {
					userData.saveData();
					currentActive.onClose();
					folderViewController.close();
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
	
	public FolderViewController getFolderView() {
		return this.folderViewController;
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
	private ActivePaneController loadScene(ActivePaneController key) {
		key.setParent(this);
		workspacePane.getChildren().clear();
		workspacePane.getChildren().add(key.getContent());
		return key;
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
		currentActive = loadScene(key);
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


	
	private void initFolderWindow() {
		try {
			Parent root;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLDestination("FolderList")));
			root = fxmlLoader.load();
			Scene scene = new Scene(root, 256, 512);
			scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
			folderStage = new Stage();
			folderStage.setScene(scene);
			
			folderStage.setTitle("Folders");
			folderStage.setResizable(false);
			
			folderViewController = fxmlLoader.getController();
			folderStage.getIcons().add(IconLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void displayFoldersWindow() {
		folderViewController.loadData(this, this.getUserData().getFolderList());
		folderViewController.reinitialize();
		folderStage.show();
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
