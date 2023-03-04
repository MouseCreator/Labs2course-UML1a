package com.example.lab23a.model;

public class UserOptions {
    boolean autoSaveOn;
    private boolean isShuffleOn;
    /**
     * Creates learn style with default settings
     */
    public UserOptions() {
        defaultSettings();
    }

    /**
     * Applies default settings.
     * More specified: shuffle terms in write mode, automatically save progress.
     */

    public void defaultSettings() {
        isShuffleOn = true;
        autoSaveOn = true;
    }
    /**
     *
     * @param isShuffleOn - option that determines, whether the terms are shuffled in write mode
     */
    public void setShuffleOn(boolean isShuffleOn) {
        this.isShuffleOn = isShuffleOn;
    }

    /**
     *
     * @return true, if terms will be shuffled in write mode
     */
    public boolean getIsShuffleOn() {
        return this.isShuffleOn;
    }

    /**
     *
     * @return true, if data is saving automatically on the background
     */
    public boolean getAutosaveOn() {
        return this.autoSaveOn;
    }

    /**
     *
     * @param autoSaveOn - option, that determines if the data will be saving constantly as a background process.
     */
    public void setAutoSaveOn(boolean autoSaveOn) {
        this.autoSaveOn = autoSaveOn;
    }
}