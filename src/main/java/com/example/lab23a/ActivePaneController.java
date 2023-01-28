package com.example.lab23a;

public abstract class ActivePaneController {

	protected WorkspaceController parentController;
	
	public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	public WorkspaceController getParent() {
		return parentController;
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
}
