package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import org.nield.dirtyfx.beans.DirtyObjectProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class CardEditorMvciModel {
    private final ObjectProperty<Flashcard> flashcard = new SimpleObjectProperty<>(null);
    private final CompositeDirtyProperty dirty = new CompositeDirtyProperty();

    public Flashcard getFlashcard() {
        return flashcard.get();
    }

    public ObjectProperty<Flashcard> flashcardProperty() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard.set(flashcard);
    }

    public Boolean getDirty() {
        return dirty.getValue();
    }

    public CompositeDirtyProperty dirtyProperty() {
        return dirty;
    }
}
