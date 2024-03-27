package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.domain.*;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class CardEditorMvciController {
    private static final FlashcardToControllerVisitor flashcardToControllerVisitor = new FlashcardToControllerVisitor();

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

    public void loadCard(Flashcard flashcard) {
        model.setController(flashcard.accept(flashcardToControllerVisitor));
    }

    public DirtyProperty dirtyProperty() {
        return model.getControllerDeepDirtyProperty();
    }

    public CardController<?> getController() {
        return model.getController();
    }
}
