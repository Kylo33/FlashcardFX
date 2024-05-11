package com.kyloapps.settings;

import atlantafx.base.theme.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class SettingsMvciModel {
    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>(null);
    private final Theme[] themes = new Theme[]{
            new PrimerLight(),
            new PrimerDark(),
            new CupertinoLight(),
            new CupertinoDark(),
            new NordLight(),
            new NordDark(),
            new Dracula(),
    };
    private final ObjectProperty<Theme> currentTheme = new SimpleObjectProperty<>(themes[0]);


    public File getCurrentDirectory() {
        return currentDirectory.get();
    }

    public ObjectProperty<File> currentDirectoryProperty() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory.set(currentDirectory);
    }

    public Theme getCurrentTheme() {
        return currentTheme.get();
    }

    public ObjectProperty<Theme> currentThemeProperty() {
        return currentTheme;
    }

    public void setCurrentTheme(Theme currentTheme) {
        this.currentTheme.set(currentTheme);
    }

    public Theme[] getThemes() {
        return themes;
    }
}
