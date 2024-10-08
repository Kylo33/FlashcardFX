package com.kyloapps.mainmvci;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import com.kyloapps.domain.Deck;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainMvciModel {
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();
    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<Page> selectedPage = new SimpleObjectProperty<>();
    private final ObservableMap<Deck, File> deckFileMap = FXCollections.observableHashMap();

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

    public Deck getCurrentDeck() {
        return currentDeck.get();
    }

    public ObjectProperty<Deck> currentDeckProperty() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck.set(currentDeck);
    }

    public ObservableMap<Deck, File> getDeckFileMap() {
        return deckFileMap;
    }
}
