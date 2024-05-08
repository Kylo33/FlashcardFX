package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import com.kyloapps.domain.Flashcard;
import javafx.beans.property.*;

public class PracticeMvciModel {
    private final IntegerProperty currentFlashcardIndex = new SimpleIntegerProperty();
    private final ObjectProperty<Flashcard> currentFlashcard = new SimpleObjectProperty<>();
    private final StringProperty question = new SimpleStringProperty(currentFlashcard.get().getQuestion());
    private final StringProperty imageUrl = new SimpleStringProperty(currentFlashcard.get().getImageUrl());

    public int getCurrentFlashcardIndex() {
        return currentFlashcardIndex.get();
    }

    public IntegerProperty currentFlashcardIndexProperty() {
        return currentFlashcardIndex;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard.get();
    }

    public ObjectProperty<Flashcard> currentFlashcardProperty() {
        return currentFlashcard;
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
}
