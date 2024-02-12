package com.kyloapps.practice;

import com.kyloapps.domain.Flashcard;
import javafx.beans.binding.Bindings;

public class PracticeMvciInteractor {
    private final PracticeMvciModel model;
    private final static int FIRST_FLASHCARD = 0;
    public PracticeMvciInteractor(PracticeMvciModel model) {
        this.model = model;

        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> {
            model.setCurrentFlashcardIndex(FIRST_FLASHCARD);
        });
        manageNextCardState();
    }

    private void manageNextCardState() {
        model.nextCardExistsProperty().bind(Bindings.createBooleanBinding(() ->  {
            int currentIndex = model.getCurrentFlashcardIndex();
            if (model.getCurrentDeck() == null) return false;
            int lastIndex = model.getCurrentDeck().getFlashcards().size() - 1;
            return currentIndex < lastIndex;
        }, model.currentFlashcardIndexProperty(), model.currentDeckProperty()));

        model.previousCardExistsProperty().bind(Bindings.createBooleanBinding(() ->  {
            int currentIndex = model.getCurrentFlashcardIndex();
            return currentIndex > FIRST_FLASHCARD;
        }, model.currentFlashcardIndexProperty(), model.currentDeckProperty()));
    }

    public void nextCurrentFlashcard() {
        int nextIndex = model.getCurrentFlashcardIndex() + 1;
        int lastFlashcardIndex = model.getCurrentDeck().getFlashcards().size() - 1;
        if (nextIndex <= lastFlashcardIndex) {
            model.setCurrentFlashcardIndex(nextIndex);
        }
    }

    public void previousCurrentFlashcard() {
        int previousIndex = model.getCurrentFlashcardIndex() - 1;
        int firstFlashcardIndex = 0;
        if (previousIndex >= firstFlashcardIndex) {
            model.setCurrentFlashcardIndex(previousIndex);
        }
    }
}
