package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class CardEditorMvciController {
    private final CardEditorMvciViewBuilder viewBuilder;
    private final CardEditorMvciModel model;
    public CardEditorMvciController() {
        model = new CardEditorMvciModel();
        CardEditorMvciInteractor interactor = new CardEditorMvciInteractor(model);
        viewBuilder = new CardEditorMvciViewBuilder(model);
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public DirtyProperty dirtyProperty() {
        return model.dirtyProperty();
    }

    public Flashcard getFlashcard() {
        return model.getFlashcard();
    }
}
