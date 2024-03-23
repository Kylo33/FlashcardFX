package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.Flashcard;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class CardEditorMvciController {
    private final CardEditorMvciViewBuilder viewBuilder;
    private final CardEditorMvciModel model;
    public CardEditorMvciController() {
        model = new CardEditorMvciModel();
        CardEditorMvciInteractor interactor = new CardEditorMvciInteractor(model);
        viewBuilder = new CardEditorMvciViewBuilder(model, interactor::changeCard);
    }

    public void loadCard(Flashcard flashcard) {
        model.setFlashcard(flashcard);
        // TODO make a copy of the flashcard, then in the save deck update the original deck.
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public DirtyProperty dirtyProperty() {
        return model.getMasterDirtyProperty();
    }

    public Flashcard getFlashcard() {
        return model.getFlashcard();
    }
}
