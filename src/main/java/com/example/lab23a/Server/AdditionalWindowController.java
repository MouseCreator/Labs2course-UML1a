package com.example.lab23a.Server;

import com.example.lab23a.WorkspaceController;

public abstract class AdditionalWindowController {
    private final WorkspaceController parent;


    public AdditionalWindowController(WorkspaceController parent) {
        this.parent = parent;
    }

    public WorkspaceController getParent() {
        return parent;
    }
}
