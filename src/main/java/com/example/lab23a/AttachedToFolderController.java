package com.example.lab23a;

import com.example.lab23a.model.Folder;

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
