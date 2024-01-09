package com.kyloapps.Model;

import com.kyloapps.DisplayableFlashcard;
import javafx.beans.property.*;

public class PracticeDeckManager {
    private ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private IntegerProperty currentIndex = new SimpleIntegerProperty(0);
    private BooleanProperty nextCardExists = new SimpleBooleanProperty();
    private BooleanProperty prevCardExists = new SimpleBooleanProperty();

    public PracticeDeckManager() {
        currentDeck.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                for (DisplayableFlashcard flashcard: oldValue.getFlashcards()) {
                    flashcard.resetDisplay();
                }
            } else {
                currentIndex.set(0);
                loadCards(newValue);
                updateNextPrev();
            }
        });
        currentIndex.addListener((observable, oldValue, newValue) -> {
            updateNextPrev();
        });
    }

    private void loadCards(Deck deck) {
        new Thread(() -> {
            deck.getFlashcards().forEach((flashcard) -> {
                flashcard.load();
            });
        }).start();
    }

    public void nextCard() {
        if (nextCardExists.get()) {
            currentIndex.set(currentIndex.get() + 1);
        }
    }

    public void prevCard() {
        if (prevCardExists.get()) {
            currentIndex.set(currentIndex.get() - 1);
        }
    }

    private void updateNextPrev() {
        nextCardExists.set(currentIndex.get() + 1 < currentDeck.get().getFlashcards().size());
        prevCardExists.set(currentIndex.get() - 1 >= 0);
    }

    public boolean isNextCardExists() {
        return nextCardExists.get();
    }

    public BooleanProperty nextCardExistsProperty() {
        return nextCardExists;
    }

    public boolean isPrevCardExists() {
        return prevCardExists.get();
    }

    public BooleanProperty prevCardExistsProperty() {
        return prevCardExists;
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

    public int getCurrentIndex() {
        return currentIndex.get();
    }

    public IntegerProperty currentIndexProperty() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex.set(currentIndex);
    }
}
