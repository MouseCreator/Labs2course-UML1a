package com.example.lab23a;


public class MenuBarController {

    private WorkspaceController parentController;
	
	public void switchToEditor() {
		parentController.loadAttachedToIndex(Pages.SET_EDITOR, parentController.getUserData().genNewIndex()); 
	}
	public void switchToSetOpen()  {
		parentController.loadWithAllFolders(Pages.SET_OPEN); 
	}
	public void viewFolderList() {
		parentController.displayFoldersWindow();
	}
	public void switchToProfile()  {
		parentController.loadPage(Pages.PROFILE);
	}
	public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	
	
	

}
