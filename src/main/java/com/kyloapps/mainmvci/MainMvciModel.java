package com.kyloapps.mainmvci;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MainMvciModel {
    private final BooleanProperty menuPageSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty editorPageSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty settingsPageSelected = new SimpleBooleanProperty(false);

    public boolean isMenuPageSelected() {
        return menuPageSelected.get();
    }

    public BooleanProperty menuPageSelectedProperty() {
        return menuPageSelected;
    }

    public void setMenuPageSelected(boolean menuPageSelected) {
        this.menuPageSelected.set(menuPageSelected);
    }

    public boolean isEditorPageSelected() {
        return editorPageSelected.get();
    }

    public BooleanProperty editorPageSelectedProperty() {
        return editorPageSelected;
    }

    public void setEditorPageSelected(boolean editorPageSelected) {
        this.editorPageSelected.set(editorPageSelected);
    }

    public boolean isSettingsPageSelected() {
        return settingsPageSelected.get();
    }

    public BooleanProperty settingsPageSelectedProperty() {
        return settingsPageSelected;
    }

    public void setSettingsPageSelected(boolean settingsPageSelected) {
        this.settingsPageSelected.set(settingsPageSelected);
    }
}
