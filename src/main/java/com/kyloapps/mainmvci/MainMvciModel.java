package com.kyloapps.mainmvci;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import com.kyloapps.domain.Deck;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class MainMvciModel {
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();
    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();

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

    public Page getSelectedPage() {
        return selectedPage.get();
    }

    public ObjectProperty<Page> selectedPageProperty() {
        return selectedPage;
    }

    public void setSelectedPage(Page selectedPage) {
        this.selectedPage.set(selectedPage);
    }
}
