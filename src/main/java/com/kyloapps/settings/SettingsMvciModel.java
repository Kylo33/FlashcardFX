package com.kyloapps.settings;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class SettingsMvciModel {
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
