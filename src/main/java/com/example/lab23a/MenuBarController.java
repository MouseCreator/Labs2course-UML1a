package com.example.lab23a;


public class MenuBarController {

    private WorkspaceController parentController;
	
	public void switchToEditor() {
		parentController.loadPage(new EditorController().load(parentController, parentController.getUserData().genNewIndex()));
	}
	public void switchToSetOpen()  {
		parentController.loadPage(new SetOpenController().load(parentController));
	}
	public void viewFolderList() {
		parentController.openAdditionalWindow(new FolderViewController().load(parentController));
	}
	public void switchToProfile()  {
		parentController.loadPage(new ProfileController().load(parentController));
	}
	public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	
	
	

}
