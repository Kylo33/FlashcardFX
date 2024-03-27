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
    private final DirtyStringProperty deckNameInput = new DirtyStringProperty("");
    private final DirtyStringProperty deckDescriptionInput = new DirtyStringProperty("");

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

    public String getDeckNameInput() {
        return deckNameInput.get();
    }

    public DirtyStringProperty deckNameInputProperty() {
        return deckNameInput;
    }

    public void setDeckNameInput(String deckNameInput) {
        this.deckNameInput.set(deckNameInput);
    }

    public String getDeckDescriptionInput() {
        return deckDescriptionInput.get();
    }

    public DirtyStringProperty deckDescriptionInputProperty() {
        return deckDescriptionInput;
    }

    public void setDeckDescriptionInput(String deckDescriptionInput) {
        this.deckDescriptionInput.set(deckDescriptionInput);
    }

    public DirtyProperty getMasterDirtyProperty() {
        return masterDirtyProperty;
    }
}
