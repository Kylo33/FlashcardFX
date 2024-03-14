package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class DeckEditorMvciModel {
    private final ObservableList<Deck> decks = FXCollections.observableArrayList(deck ->
        new Observable[]{deck.titleProperty(), deck.descriptionProperty(), deck.getFlashcards()}
    );
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final StringProperty newDeckName = new SimpleStringProperty();
    private final StringProperty newDeckDescription = new SimpleStringProperty();
    private final DirtyStringProperty editingDeckName = new DirtyStringProperty("");
    private final DirtyStringProperty editingDeckDescription = new DirtyStringProperty("");
    private final DirtyObservableList<CardEditorMvciController> cardEditorControllers = new DirtyObservableList<>(); // STORE IN COMPOSITE DIRTY PROPERTY BELOW
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

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

    public DirtyStringProperty editingDeckNameProperty() {
        return editingDeckName;
    }

    public void setEditingDeckName(String editingDeckName) {
        this.editingDeckName.set(editingDeckName);
    }

    public String getEditingDeckDescription() {
        return editingDeckDescription.get();
    }

    public DirtyStringProperty editingDeckDescriptionProperty() {
        return editingDeckDescription;
    }

    public void setEditingDeckDescription(String editingDeckDescription) {
        this.editingDeckDescription.set(editingDeckDescription);
    }

    public CompositeDirtyProperty compositeDirtyPropertyProperty() {
        return compositeDirtyProperty;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }

    public DirtyObservableList<CardEditorMvciController> getCardEditorControllers() {
        return cardEditorControllers;
    }
}
