package com.kyloapps.deckeditor.cardeditor;

import javafx.collections.ListChangeListener;

public class CardEditorMvciInteractor {
    private final CardEditorMvciModel model;
    public CardEditorMvciInteractor(CardEditorMvciModel model) {
        this.model = model;
        model.getMasterDirtyProperty().add(model.flashcardProperty());
        model.getMasterDirtyProperty().add(model.getTextFieldTileDeepDirtyList());
    }

    public void changeCard() {
        model.getTextFieldTileDeepDirtyList().getCurrentObservableList().clear();
    }
}
