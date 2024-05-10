package com.kyloapps.practice;

import com.kyloapps.domain.Flashcard;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.image.Image;

public class PracticeMvciInteractor {

    private final PracticeMvciModel model;

    public PracticeMvciInteractor(PracticeMvciModel model) {
        this.model = model;
        manageNextPrevExists();
        bindQuestion();
        bindImage();
    }

    private void bindImage() {
        model.imageProperty().bind(Bindings.createObjectBinding(() -> {
            if (model.getCurrentDeck() == null) return null;
            String imageUrl = getCurrentFlashcard().getImageUrl();
            return imageUrl == null || imageUrl.isBlank() ? null : new Image(imageUrl.strip(), true);
        }, model.currentDeckProperty(), model.currentDeckIndexProperty()));
    }

    private Flashcard getCurrentFlashcard() {
        return model.getCurrentDeck().getFlashcards().get(model.getCurrentDeckIndex());
    }

    private void bindQuestion() {
        model.questionProperty().bind(Bindings.createStringBinding(() -> {
            if (model.getCurrentDeck() == null) return "";
            return getCurrentFlashcard().getQuestion();
        }, model.currentDeckProperty(), model.currentDeckIndexProperty()));
    }

    private void manageNextPrevExists() {
        BooleanBinding nextExistsBinding = new BooleanBinding() {
            {
                bind(model.currentDeckProperty(), model.currentDeckIndexProperty());
                if (model.getCurrentDeck() != null)
                    bind(model.getCurrentDeck().getFlashcards());
                model.currentDeckProperty().addListener((observable, oldValue, newValue) -> {
                    if (oldValue != null)
                        unbind(oldValue.getFlashcards());
                    if (newValue != null)
                        bind(newValue.getFlashcards());
                });
            }
            @Override
            protected boolean computeValue() {
                if (model.getCurrentDeck() == null)
                    return false;
                return model.getCurrentDeckIndex() < model.getCurrentDeck().getFlashcards().size() - 1;
            }
        };
        model.nextExistsProperty().bind(nextExistsBinding);

        model.prevExistsProperty().bind(Bindings.createBooleanBinding(
                () -> model.getCurrentDeckIndex() > 0,
                model.currentDeckIndexProperty()));
    }

    public void reload() {
        model.setCurrentDeckIndex(0);
    }
}
