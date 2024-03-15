package com.kyloapps.deckeditor.cardeditor;

import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;

public class CardEditorMvciInteractor {
    private final CardEditorMvciModel model;
    public CardEditorMvciInteractor(CardEditorMvciModel model) {
        this.model = model;
        bindTextFieldTileDirtyProperties();
    }

    /** Bind the getTextFieldTileDirtyProperties to the getTextFieldTiles mapped to their CompositeDirtyProperties */
    private void bindTextFieldTileDirtyProperties() {
        Bindings.bindContent(
                model.getTextFieldTileDirtyProperties(),
                EasyBind.mapBacked(model.getTextFieldTiles(), TextFieldTile::getCompositeDirtyProperty)
        );
    }
}
