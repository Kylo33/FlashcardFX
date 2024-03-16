package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.awt.*;

public class CardEditorMvciController {
    private final CardEditorMvciViewBuilder viewBuilder;
    private final CardEditorMvciModel model;
    public CardEditorMvciController() {
        model = new CardEditorMvciModel();
        CardEditorMvciInteractor interactor = new CardEditorMvciInteractor(model);
        viewBuilder = new CardEditorMvciViewBuilder(model);
    }

    public void loadCard(Flashcard flashcard) {
        model.setFlashcard(flashcard);
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public CompositeDirtyProperty dirtyProperty() {
        return model.getCompositeDirtyProperty();
    }

    public Flashcard getFlashcard() {
        return model.getFlashcard();
    }
}
