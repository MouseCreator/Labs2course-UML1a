package com.example.lab23a;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Class that controls the UI controls of some specific page.
 */
public abstract class ActivePaneController {

	protected WorkspaceController parentController;
    private Pane content;

    /**
     *
     * @return workspace controller, from which the data to the controller is loaded
     */
	public WorkspaceController getParent() {
		return parentController;
	}

    /**
     *
     * @param parentController - workspace controller, from which all the data will be loaded
     * @return initialized controller that can be used as current active
     */
    public ActivePaneController load(WorkspaceController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(parentController.getClass().getResource(getFilename()));
        try {
            Pane pane = fxmlLoader.load();
            ActivePaneController controller = fxmlLoader.getController();
            controller.content = pane;
            controller.parentController = parentController;
            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ActivePaneController() {

    }

    /**
     * Prepare elements of the interface for user
     */
    public abstract void initContent();

    /**
     * Action to do, when the pane is closed
     */
    public void onClose() {
    	saveUserData();
    }
    /**
     * 
     * @return true if page can be instantly closed
     */
    public boolean onCloseRequest() {
    	return true;
    }
    public void saveUserData() {
        parentController.getUserData().autoSave();
    }

    public abstract String getFilename();

    public Pane getContent() {
        return content;
    }
}
