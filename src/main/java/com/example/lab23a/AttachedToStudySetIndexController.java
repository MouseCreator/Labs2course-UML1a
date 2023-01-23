package com.example.lab23a;

import com.example.lab23a.model.SetIndex;

public abstract class AttachedToStudySetIndexController extends ActivePaneController {
	protected SetIndex index;
	
	public SetIndex getIndex() {
		return index;
	}
	public void setIndex(SetIndex index) {
		this.index = index;
	}
	public AttachedToStudySetIndexController(WorkspaceController parent) {
		super(parent);
	}
	public AttachedToStudySetIndexController() {
		super();
	}
	@Override
	public void onClose() {
		getParent().getUserData().autoSave();
	}
}
