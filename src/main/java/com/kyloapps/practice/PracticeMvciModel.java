package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import com.kyloapps.domain.Flashcard;
import javafx.beans.property.*;

public class PracticeMvciModel {
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final IntegerProperty currentFlashcardIndex = new SimpleIntegerProperty();
    private final BooleanProperty previousCardExists = new SimpleBooleanProperty();
    private final BooleanProperty nextCardExists = new SimpleBooleanProperty();

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

    public boolean isPreviousCardExists() {
        return previousCardExists.get();
    }

    public BooleanProperty previousCardExistsProperty() {
        return previousCardExists;
    }

    public void setPreviousCardExists(boolean previousCardExists) {
        this.previousCardExists.set(previousCardExists);
    }

    public boolean isNextCardExists() {
        return nextCardExists.get();
    }

    public BooleanProperty nextCardExistsProperty() {
        return nextCardExists;
    }

    public void setNextCardExists(boolean nextCardExists) {
        this.nextCardExists.set(nextCardExists);
    }
}
