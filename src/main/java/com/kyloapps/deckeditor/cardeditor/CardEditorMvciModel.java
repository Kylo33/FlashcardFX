package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class CardEditorMvciModel {
    private final ObjectProperty<Flashcard> flashcard = new SimpleObjectProperty<>(null);
    private final DirtyObservableList<TextFieldTile> textFieldTiles = new DirtyObservableList<>();
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public Flashcard getFlashcard() {
        return flashcard.get();
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard.set(flashcard);
    }

    public ObjectProperty<Flashcard> flashcardProperty() {
        return flashcard;
    }

    public DirtyObservableList<TextFieldTile> getTextFieldTiles() {
        return textFieldTiles;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }
}
