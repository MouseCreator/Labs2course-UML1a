package com.example.lab23a;

import com.example.lab23a.model.Folder;

/**
 * Class for controllers, that are dependent on some folder of the study sets.
 * Used to view, modify or get information from the folder.
 * It includes working with all available study sets, since all sets considered as a folder.
 */
public abstract class AttachedToFolderController extends ActivePaneController {
	protected Folder folder;

	public AttachedToFolderController() {
		super();
	}

	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public AttachedToFolderController load(WorkspaceController parent, Folder folder) {
		AttachedToFolderController a = ((AttachedToFolderController) super.load(parent));
		a.setFolder(folder);
		return a;
	}
	public AttachedToFolderController load(WorkspaceController parent) {
		AttachedToFolderController a = ((AttachedToFolderController) super.load(parent));
		a.setFolder(new Folder(Folder.ALL_SETS));
		return a;
	}
	public AttachedToFolderController(Folder folder) {
		super();
		this.folder = folder;
	}
	@Override
	public void onClose() {
		super.onClose();
	}
}
