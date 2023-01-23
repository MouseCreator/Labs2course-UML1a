package com.example.lab23a;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.UserData;

public class WorkspaceController implements Initializable {

    @FXML
    private Pane menuBarPane;

    @FXML
    private Pane workspacePane;
    
    
    
    private final HashMap<Pages, String> destination = new HashMap<>();
    
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
		loadWithAllFolders(Pages.SET_OPEN);
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
		initSceneFiles();
	}
	private void initSceneFiles() {
		try {
			initDestination();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("MenuBar")));
			menuBarPane.getChildren().add(fxmlLoader.load());
			MenuBarController c = fxmlLoader.getController();
			c.setParent(this);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
	private ActivePaneController loadScene(Pages key) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(destination.get(key)));
		Pane toAdd;
		try {
			toAdd = fxmlLoader.load();
			workspacePane.getChildren().clear();
			workspacePane.getChildren().add(toAdd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addController(fxmlLoader);
    }
	
	public void loadPage(Pages key) {
		if (closeActiveController())
			loadNext(key);
		else
			this.proceedClosing = ()->loadNext(key);
		
	}
	
	private void loadNext(Pages key) {
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
	
	public void loadAttachedToIndex(Pages key, SetIndex index) {
		if(closeActiveController()) {
			loadNextAttachedToIndex(key, index);
		}
		else {
			this.proceedClosing = ()->loadNextAttachedToIndex(key, index);
		}
	}
	
	private void loadNextAttachedToIndex(Pages key, SetIndex index) {
		if (currentActive != null)
			currentActive.onClose();
		AttachedToStudySetIndexController controller = (AttachedToStudySetIndexController)loadScene(key);
		controller.setIndex(index);
		controller.initContent();
		currentActive = controller;
	}
	
	public void loadAttachedToFolder(Pages key, Folder folder) {
		if(closeActiveController())
			loadNextAttachedToFolder(key, folder);
		else
			this.proceedClosing = ()->loadNextAttachedToFolder(key, folder);
		
	}
	
	public void proceedClosingOperation() {
		this.proceedClosing.run();
		this.proceedClosing = () -> {};
	}
	
	private void loadNextAttachedToFolder(Pages key, Folder folder) {
		if (currentActive != null)
			currentActive.onClose();
		AttachedToFolderController controller = (AttachedToFolderController)loadScene(key);
		controller.setFolder(folder);
		controller.initContent();
		currentActive = controller;
		lastActiveFolder = folder;
	}
	public void loadWithAllFolders(Pages key) {
		loadAttachedToFolder(key, new Folder(Folder.ALL_SETS));
	}

	private void initDeletionPopup() {
		this.confirmWindow = new PopUpWindows();
	}
	
	private void initDestination() throws IOException {
		destination.put(Pages.SET_OPEN, FileBuilder.FXMLdestination("SetOpen"));
		destination.put(Pages.SET_EDITOR, FileBuilder.FXMLdestination("SetEditor"));
		destination.put(Pages.SET_INFO, FileBuilder.FXMLdestination("SetInfo"));
		destination.put(Pages.SET_WRITE, FileBuilder.FXMLdestination("Write"));
		destination.put(Pages.PROFILE, FileBuilder.FXMLdestination("Profile"));
	}
	
	private ActivePaneController addController(FXMLLoader fxmlLoader) {
		ActivePaneController controller = fxmlLoader.getController();
		if (controller == null) {
			System.err.println("Error casting controller");
		} else {
			controller.setParent(this);
		}
		return controller;
	}
	
	private void initFolderWindow() {
		try {
			Parent root;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(FileBuilder.FXMLdestination("FolderList")));
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
		folderViewController.reinit();
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
