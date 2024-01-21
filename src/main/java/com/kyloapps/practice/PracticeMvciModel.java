package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import com.kyloapps.domain.Flashcard;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PracticeMvciModel {
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final IntegerProperty currentFlashcardIndex = new SimpleIntegerProperty();

    public Deck getCurrentDeck() {
        return currentDeck.get();
    }

    public ObjectProperty<Deck> currentDeckProperty() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck.set(currentDeck);
    }

    public int getCurrentFlashcardIndex() {
        return currentFlashcardIndex.get();
    }

    public IntegerProperty currentFlashcardIndexProperty() {
        return currentFlashcardIndex;
    }

    public void setCurrentFlashcardIndex(int currentFlashcardIndex) {
        this.currentFlashcardIndex.set(currentFlashcardIndex);
    }
}
