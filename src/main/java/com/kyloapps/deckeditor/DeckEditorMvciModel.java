package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.utils.DeepDirtyList;
import com.kyloapps.domain.Deck;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.io.File;

public class DeckEditorMvciModel {
    // Store the deck and current deck
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final ObservableList<Deck> decks = FXCollections.observableArrayList(deck ->
        new Observable[]{deck.titleProperty(), deck.descriptionProperty(), deck.getFlashcards()}
    );

    // Store the current directory
    private final ObjectProperty<File> currentDirectory = new SimpleObjectProperty<>();

    // Store a copy of the Map from Deck to File
    private final ObservableMap<Deck, File> deckFileMap = FXCollections.observableHashMap();

    // Store all the CardEditorController s
    private final ObservableList<CardEditorMvciController> cardEditorControllers = FXCollections.observableArrayList();

    // Store the new deck details (for when editing or creating a deck)
    private final StringProperty creationDeckNameInput = new SimpleStringProperty("");
    private final StringProperty creationDeckDescriptionInput = new SimpleStringProperty("");

    private final DirtyStringProperty editingDeckNameInput = new DirtyStringProperty("");
    private final DirtyStringProperty editingDeckDescriptionInput = new DirtyStringProperty("");

    // Store a master CompositeDirtyProperty to determine if changes have been made.
    private final DeepDirtyList<CardEditorMvciController> deepDirtyList = new DeepDirtyList<>(cardEditorControllers, CardEditorMvciController::dirtyProperty);
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    // Getters and setters
    public Deck getCurrentDeck() {
        return currentDeck.get();
    }

    public ObjectProperty<Deck> currentDeckProperty() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck.set(currentDeck);
    }

    public ObservableList<Deck> getDecks() {
        return decks;
    }

    public ObservableList<CardEditorMvciController> getCardEditorControllers() {
        return cardEditorControllers;
    }

    public String getCreationDeckNameInput() {
        return creationDeckNameInput.get();
    }

    public StringProperty creationDeckNameInputProperty() {
        return creationDeckNameInput;
    }

    public void setCreationDeckNameInput(String creationDeckNameInput) {
        this.creationDeckNameInput.set(creationDeckNameInput);
    }

    public String getCreationDeckDescriptionInput() {
        return creationDeckDescriptionInput.get();
    }

    public StringProperty creationDeckDescriptionInputProperty() {
        return creationDeckDescriptionInput;
    }

    public void setCreationDeckDescriptionInput(String creationDeckDescriptionInput) {
        this.creationDeckDescriptionInput.set(creationDeckDescriptionInput);
    }

    public String getEditingDeckNameInput() {
        return editingDeckNameInput.get();
    }

    public DirtyStringProperty editingDeckNameInputProperty() {
        return editingDeckNameInput;
    }

    public void setEditingDeckNameInput(String editingDeckNameInput) {
        this.editingDeckNameInput.set(editingDeckNameInput);
    }

    public String getEditingDeckDescriptionInput() {
        return editingDeckDescriptionInput.get();
    }

    public DirtyStringProperty editingDeckDescriptionInputProperty() {
        return editingDeckDescriptionInput;
    }

    public void setEditingDeckDescriptionInput(String editingDeckDescriptionInput) {
        this.editingDeckDescriptionInput.set(editingDeckDescriptionInput);
    }

    public DirtyProperty getDeepDirtyList() {
        return deepDirtyList;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }

    public ObservableMap<Deck, File> getDeckFileMap() {
        return deckFileMap;
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

    public CompositeDirtyProperty compositeDirtyPropertyProperty() {
        return compositeDirtyProperty;
    }
}
