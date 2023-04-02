package com.example.lab23a.model.builder;

import com.example.lab23a.EditorController;

public class SaveCommand implements Command {
    private final EditorController controller;
    public SaveCommand(EditorController controller) {
        this.controller = controller;
    }
    @Override
    public void execute() {
        controller.onSave();
    }
}
