package com.example.lab23a;


public class MenuBarController {

    private WorkspaceController parentController;
	
	public void switchToEditor() {
		parentController.loadPage(new EditorController(parentController.getUserData().genNewIndex()));
	}
	public void switchToSetOpen()  {
		parentController.loadPage(new SetOpenController());
	}
	public void viewFolderList() {
		parentController.displayFoldersWindow();
	}
	public void switchToProfile()  {
		parentController.loadPage(new ProfileController());
	}
	public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	
	
	

}
