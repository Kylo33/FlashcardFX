package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.CardControllerVisitor;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.domain.AnswerOption;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import java.util.stream.Collectors;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class MultipleChoiceMvciController
        implements CardController<MultipleChoiceFlashcard> {
    private final MultipleChoiceMvciModel model;
    private final MultipleChoiceMvciViewBuilder viewBuilder;
    private final MultipleChoiceMvciInteractor interactor;

    public MultipleChoiceMvciController() {
        this.model = new MultipleChoiceMvciModel();
        this.interactor = new MultipleChoiceMvciInteractor(model);
        this.viewBuilder = new MultipleChoiceMvciViewBuilder(model);
    }

    @Override
    public Region getView() {
        return this.viewBuilder.build();
    }

    @Override
    public DirtyProperty getDirtyProperty() {
        return model.getCompositeDirtyProperty();
    }

    @Override
    public <T> T accept(CardControllerVisitor<T> cardControllerVisitor) {
        return cardControllerVisitor.visit(this);
    }

    @Override
    // Copies the flashcard, so it will not be changed until the changes are
    // saved.
    public void loadCard(MultipleChoiceFlashcard flashcard) {
        model.setQuestion(flashcard.getQuestion());
        model.getOptions().setAll(flashcard.getOptions());
        if (flashcard.getImageUrl() != null)
            model.imageUrlProperty().set(flashcard.getImageUrl());
    }

    @Override
    public MultipleChoiceFlashcard toFlashcard() {
        MultipleChoiceFlashcard result = new MultipleChoiceFlashcard();
        result.setQuestion(model.getQuestion());
        result.getOptions().setAll(model.getOptions());
        result.setImageUrl(model.getImageUrl());
        return result;
    }
}
