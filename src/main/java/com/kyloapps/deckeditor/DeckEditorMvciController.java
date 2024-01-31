package com.kyloapps.deckeditor;

import javafx.scene.layout.Region;

public class DeckEditorMvciController {
    private final DeckEditorMvciViewBuilder viewBuilder;

    public DeckEditorMvciController() {
        DeckEditorMvciModel model = new DeckEditorMvciModel();
        DeckEditorMvciInteractor interactor = new DeckEditorMvciInteractor(model);
        viewBuilder = new DeckEditorMvciViewBuilder(model);
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
