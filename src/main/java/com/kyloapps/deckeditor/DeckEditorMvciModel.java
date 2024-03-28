package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.utils.DeepDirtyList;
import com.kyloapps.domain.Deck;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class DeckEditorMvciModel {
    // Store the deck and current deck
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final ObservableList<Deck> decks = FXCollections.observableArrayList(deck ->
        new Observable[]{deck.titleProperty(), deck.descriptionProperty(), deck.getFlashcards()}
    );

    // Store all the CardEditorController s
    private final ObservableList<CardEditorMvciController> cardEditorControllers = FXCollections.observableArrayList();

    // Store the new deck details (for when editing or creating a deck)
    private final DirtyStringProperty creationDeckNameInput = new DirtyStringProperty("");
    private final DirtyStringProperty creationDeckDescriptionInput = new DirtyStringProperty("");

    private final DirtyStringProperty editingDeckNameInput = new DirtyStringProperty("");
    private final DirtyStringProperty editingDeckDescriptionInput = new DirtyStringProperty("");

    // Store a master CompositeDirtyProperty to determine if changes have been made.
    private final DirtyProperty masterDirtyProperty = new DeepDirtyList<>(cardEditorControllers, CardEditorMvciController::dirtyProperty);

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

    public DirtyStringProperty creationDeckNameInputProperty() {
        return creationDeckNameInput;
    }

    public void setCreationDeckNameInput(String creationDeckNameInput) {
        this.creationDeckNameInput.set(creationDeckNameInput);
    }

    public String getCreationDeckDescriptionInput() {
        return creationDeckDescriptionInput.get();
    }

    public DirtyStringProperty creationDeckDescriptionInputProperty() {
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

    public DirtyProperty getMasterDirtyProperty() {
        return masterDirtyProperty;
    }
}
