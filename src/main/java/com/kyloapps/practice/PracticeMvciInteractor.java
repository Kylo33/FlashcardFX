package com.kyloapps.practice;

import javafx.beans.binding.Bindings;

public class PracticeMvciInteractor {

    private final PracticeMvciModel model;

    public PracticeMvciInteractor(PracticeMvciModel model) {
        this.model = model;
        manageNextPrevExists();
        bindQuestion();
    }

    private void bindQuestion() {
        model.questionProperty().bind(Bindings.createStringBinding(() -> {
            if (model.getCurrentDeck() == null) return "";
            return model.getCurrentDeck().getFlashcards().get(model.getCurrentDeckIndex()).getQuestion();
        }, model.currentDeckProperty(), model.currentDeckIndexProperty()));
    }

    private void manageNextPrevExists() {
        model.nextExistsProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    if (model.getCurrentDeck() == null)
                        return false;
                    return model.getCurrentDeckIndex() < model.getCurrentDeck().getFlashcards().size() - 1;
                },
                model.currentDeckProperty(),
                model.currentDeckIndexProperty()));

        model.prevExistsProperty().bind(Bindings.createBooleanBinding(
                () -> model.getCurrentDeckIndex() > 0,
                model.currentDeckIndexProperty()));
    }

    public void reload() {
        model.setCurrentDeckIndex(0);
    }
}
