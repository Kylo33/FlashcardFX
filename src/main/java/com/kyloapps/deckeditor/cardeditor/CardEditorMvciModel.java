package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class CardEditorMvciModel {
    private final ObjectProperty<Flashcard> flashcard = new SimpleObjectProperty<>(null);
    private final DirtyObservableList<DirtyProperty> dirtyProperties = new DirtyObservableList<>();

    public Flashcard getFlashcard() {
        return flashcard.get();
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard.set(flashcard);
    }

    public ObjectProperty<Flashcard> flashcardProperty() {
        return flashcard;
    }

    public DirtyObservableList<DirtyProperty> getDirtyProperties() {
        return dirtyProperties;
    }
}
