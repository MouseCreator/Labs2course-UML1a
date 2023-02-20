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
	public AttachedToStudySetIndexController() {
		super();
	}

	public AttachedToStudySetIndexController load(WorkspaceController parent, SetIndex index) {
		AttachedToStudySetIndexController result = (AttachedToStudySetIndexController) super.load(parent);
		result.index = index;
		return result;
	}
	public AttachedToStudySetIndexController(SetIndex index) {
		super();
		this.index = index;
	}
	@Override
	public void onClose() {
		super.onClose();
	}
}
