package com.example.lab23a;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class ActivePaneController {

	protected WorkspaceController parentController;
    private Pane content;

    public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	public WorkspaceController getParent() {
		return parentController;
	}

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
    
    public abstract void initContent();
    
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
