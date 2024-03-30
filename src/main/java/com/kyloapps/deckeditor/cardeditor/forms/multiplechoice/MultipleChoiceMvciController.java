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
        model.getQuestionTile().getTextFields().get(0).setText(
                flashcard.getQuestion());

        model.getOptionTiles().clear();
        for (AnswerOption<StringProperty> option : flashcard.getOptions()) {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption(
                    "Answer Option", "Enter an answer choice.");
            result.getTextFields().get(0).setText(option.getContent().get());
            result.setCorrect(option.isCorrect());
            model.getOptionTiles().add(result);
        }
    }

    @Override
    public MultipleChoiceFlashcard toFlashcard() {
        MultipleChoiceFlashcard result = new MultipleChoiceFlashcard();
        result.setQuestion(
                model.getQuestionTile().getTextFields().get(0).getText());
        result.setOptions(model.getOptionTiles()
                .stream()
                .map(optionTile
                        -> new AnswerOption<>(optionTile.isCorrect(),
                        optionTile.getTextFields().get(0).getText()))
                .collect(Collectors.toList()));
        result.setImageUrl(model.getImageTile().getTextFields().get(0).getText());
        return result;
    }
}
