package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.DirtyUtils;

public class CardEditorMvciInteractor {
    private final CardEditorMvciModel model;
    public CardEditorMvciInteractor(CardEditorMvciModel model) {
        this.model = model;
        bindCompositeDirtyProperty();
    }

    /** Bind the compositeDirtyProperty to the compositeDirtyProperties and the TextFieldTiles DirtyList */
    private void bindCompositeDirtyProperty() {
        DirtyUtils.bindCompositeDirtyProperty(
                model.getCompositeDirtyProperty(),
                model.getTextFieldTiles(),
                TextFieldTile::getCompositeDirtyProperty
        );
    }
}
