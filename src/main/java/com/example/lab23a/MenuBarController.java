package com.example.lab23a;

/**
 * Class controls the top menu-bar of the screen
 */
public class MenuBarController {

    private WorkspaceController parentController;

	/**
	 * Loads set editor page
	 */
	public void switchToEditor() {
		parentController.loadPage(new EditorController().load(parentController, parentController.getUserData().generateNewIndex()));
	}

	/**
	 * Loads set opener page
	 */
	public void switchToSetOpen()  {
		parentController.loadPage(new SetOpenController().load(parentController));
	}

	/**
	 * Opens folder list
	 */
	public void viewFolderList() {
		parentController.openAdditionalWindow(new FolderViewController().load(parentController));
	}

	/**
	 * Loads profile page
	 */
	public void switchToProfile()  {
		parentController.loadPage(new ProfileController().load(parentController));
	}

	/**
	 * Changes the creator of the controller
	 * @param controller - the controller that contains the menu bar
	 */
	public void setParent(WorkspaceController controller) {
		parentController = controller;
	}
	
	
	

}
