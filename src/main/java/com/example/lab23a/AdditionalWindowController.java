package com.example.lab23a;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class AdditionalWindowController {
    private WorkspaceController parent;

    private Pane content;

    private int width;
    private int height;

    public abstract String getFilename();
    public AdditionalWindowController() {
    }

    protected AdditionalWindowController load(WorkspaceController parent) {
        this.parent = parent;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(parent.getClass().getResource(getFilename()));
        try {
            Pane pane = fxmlLoader.load();
            AdditionalWindowController controller = fxmlLoader.getController();
            controller.parent = parent;
            controller.content = pane;
            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WorkspaceController getParent() {
        return parent;
    }
}
