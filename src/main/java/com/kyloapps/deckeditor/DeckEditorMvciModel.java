package com.kyloapps.deckeditor;

import com.kyloapps.domain.Deck;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeckEditorMvciModel {
    private final ObservableList<Deck> decks = FXCollections.observableArrayList(deck -> {
        return new Observable[]{deck.titleProperty(), deck.descriptionProperty(), deck.getFlashcards()};
    });
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final StringProperty newDeckName = new SimpleStringProperty();
    private final StringProperty newDeckDescription = new SimpleStringProperty();
    private final StringProperty editingDeckName = new SimpleStringProperty();
    private final StringProperty editingDeckDescription = new SimpleStringProperty();

    public ObservableList<Deck> getDecks() {
        return decks;
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

    public String getNewDeckName() {
        return newDeckName.get();
    }

    public StringProperty newDeckNameProperty() {
        return newDeckName;
    }

    public void setNewDeckName(String newDeckName) {
        this.newDeckName.set(newDeckName);
    }

    public String getNewDeckDescription() {
        return newDeckDescription.get();
    }

    public StringProperty newDeckDescriptionProperty() {
        return newDeckDescription;
    }

    public void setNewDeckDescription(String newDeckDescription) {
        this.newDeckDescription.set(newDeckDescription);
    }

    public String getEditingDeckName() {
        return editingDeckName.get();
    }

    public StringProperty editingDeckNameProperty() {
        return editingDeckName;
    }

    public void setEditingDeckName(String editingDeckName) {
        this.editingDeckName.set(editingDeckName);
    }

    public String getEditingDeckDescription() {
        return editingDeckDescription.get();
    }

    public StringProperty editingDeckDescriptionProperty() {
        return editingDeckDescription;
    }

    public void setEditingDeckDescription(String editingDeckDescription) {
        this.editingDeckDescription.set(editingDeckDescription);
    }
}
