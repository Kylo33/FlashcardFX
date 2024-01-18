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
    private final ObjectProperty<Pages> pageSelected = new SimpleObjectProperty<>();
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();
    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>();

    public ObservableList<Deck> getDecks() {
        return decks;
    }

    public File getCurrentDirectory() {
        return currentDirectory.get();
    }

    public ObjectProperty<File> currentDirectoryProperty() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory.set(currentDirectory);
    }

    public Pages getPageSelected() {
        return pageSelected.get();
    }

    public ObjectProperty<Pages> pageSelectedProperty() {
        return pageSelected;
    }

    public void setPageSelected(Pages pageSelected) {
        this.pageSelected.set(pageSelected);
    }
}
