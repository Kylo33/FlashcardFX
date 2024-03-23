package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.dirty.DeepDirtyList;
import com.kyloapps.domain.Flashcard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyObjectProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class CardEditorMvciModel {
    private final DirtyObjectProperty<Flashcard> flashcard = new DirtyObjectProperty<>(null);
    private final ObservableList<ObservableList<TextFieldTile>> textFieldTileLists = FXCollections.observableArrayList();
    private final ObservableList<DeepDirtyList<TextFieldTile>> textFieldTileDeepDirtyList = FXCollections.observableArrayList();
    private final CompositeDirtyProperty masterDirtyProperty = new CompositeDirtyProperty();

    public Flashcard getFlashcard() {
        return flashcard.getValue();
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard.setValue(flashcard);
    }

    public DirtyObjectProperty<Flashcard> flashcardProperty() {
        return flashcard;
    }

    public ObservableList<ObservableList<TextFieldTile>> getTextFieldTileLists() {
        return textFieldTileLists;
    }

    public CompositeDirtyProperty getMasterDirtyProperty() {
        return masterDirtyProperty;
    }

    public DeepDirtyList<TextFieldTile> getTextFieldTileDeepDirtyList() {
        return textFieldTileDeepDirtyList;
    }
}
