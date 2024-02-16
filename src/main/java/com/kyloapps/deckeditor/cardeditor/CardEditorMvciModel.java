package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CardEditorMvciModel {
    private final ObjectProperty<Flashcard> flashcard = new SimpleObjectProperty<>();

    public Flashcard getFlashcard() {
        return flashcard.get();
    }

    public ObjectProperty<Flashcard> flashcardProperty() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard.set(flashcard);
    }
}
