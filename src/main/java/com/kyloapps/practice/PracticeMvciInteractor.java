package com.kyloapps.practice;

import com.kyloapps.domain.Flashcard;

public class PracticeMvciInteractor {
    private final PracticeMvciModel model;
    private final static int FIRST_FLASHCARD = 0;
    public PracticeMvciInteractor(PracticeMvciModel model) {
        this.model = model;
        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> model.setCurrentFlashcardIndex(FIRST_FLASHCARD));
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
