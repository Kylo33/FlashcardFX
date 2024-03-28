package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.CardControllerVisitor;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.domain.TableFlashcard;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class TableMvciController implements CardController<TableFlashcard> {

    private final TableMvciModel model;
    private final TableMvciInteractor interactor;
    private final TableMvciViewBuilder viewBuilder;

    public TableMvciController(){
        this.model = new TableMvciModel();
        this.interactor = new TableMvciInteractor(model);
        this.viewBuilder = new TableMvciViewBuilder(model);
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
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
    public void loadCard(TableFlashcard flashcard) {
        int rows = flashcard.getHeaders().size();
        int columns = flashcard.getOptions().size();

        model.setRowCount(rows);
        model.setColumnCount(columns);

        model.getHeaders().setTextFieldCount(columns);
        model.getQuestionTile().getTextFields().get(0).setText(flashcard.getQuestion());

        for (int i = 0, c = flashcard.getHeaders().size(); i < c; i++) {
            model.getHeaders().getTextFields().get(i).setText(flashcard.getHeaders().get(i).get());
        }

        model.getOptionTiles().clear();
        for (int i = 0; i < rows; i++) {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption("Answer Option", "Enter an answer choice.");
            result.setCorrect(flashcard.getOptions().get(i).isCorrect());

            result.setTextFieldCount(columns);
            for (int j = 0; j < columns; j++) {
                String flashcardText = flashcard.getOptions().get(i).getContent().get(j).get();
                result.getTextFields().get(j).setText(flashcardText);
            }

            model.getOptionTiles().add(result);
        }
    }
}
