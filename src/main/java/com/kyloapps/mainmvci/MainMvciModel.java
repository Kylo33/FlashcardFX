package com.kyloapps.mainmvci;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import com.kyloapps.domain.Deck;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class MainMvciModel {
    private final BooleanProperty menuPageSelected = new SimpleBooleanProperty(true);
    private final BooleanProperty editorPageSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty settingsPageSelected = new SimpleBooleanProperty(false);
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();


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

    public ObservableList<Deck> getDecks() {
        return decks;
    }

    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>(null);

    public File getCurrentDirectory() {
        return currentDirectory.get();
    }

    public ObjectProperty<File> currentDirectoryProperty() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory.set(currentDirectory);
    }
}
