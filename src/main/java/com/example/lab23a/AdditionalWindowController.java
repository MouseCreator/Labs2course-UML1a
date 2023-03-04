package com.example.lab23a;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class for secondary windows, created by main window
 */
public abstract class AdditionalWindowController {
    protected WorkspaceController parent;

    private Pane content;

    private int width = 720;
    private int height = 480;

    protected Stage stage;

    /**
     * Provides access for additional window to its scene.
     * @param stage - stage, created by workspace controller and opened as additional window
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @return filename of the FXML file to load content
     */
    public abstract String getFilename();
    public AdditionalWindowController() {
    }

    /**
     *
     * @param parent - workspace controller to load data from
     * @return initialized additional window that may be opened
     */
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

    /**
     *
     * @return workspace controller, that created the additional window
     */
    public WorkspaceController getParent() {
        return parent;
    }

    /**
     *
     * @return root element for page's content
     */
    public Pane getContent() {
        return content;
    }

    /**
     *
     * @return height in pixels of the created window
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return width in pixels of the created window
     */

    public int getWidth() {
        return width;
    }

    public abstract String getTitle();

    /**
     * Changes the height of the window
     * @param height - new height
     */
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * Changes the width of the window
     * @param width - new width
     */
    protected void setWidth(int width) {
        this.width = width;
    }
}
