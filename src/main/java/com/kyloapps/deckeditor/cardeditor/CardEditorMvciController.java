package com.kyloapps.deckeditor.cardeditor;

import javafx.scene.layout.Region;

public class CardEditorMvciController {
    private final CardEditorMvciViewBuilder viewBuilder;
    public CardEditorMvciController() {
        CardEditorMvciModel model = new CardEditorMvciModel();
        CardEditorMvciInteractor interactor = new CardEditorMvciInteractor(model);
        viewBuilder = new CardEditorMvciViewBuilder(model);
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
