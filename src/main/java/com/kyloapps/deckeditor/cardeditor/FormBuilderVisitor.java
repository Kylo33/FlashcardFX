package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import com.kyloapps.domain.Visitor;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.nield.dirtyfx.beans.DirtyStringProperty;

import java.util.function.Supplier;

import static com.kyloapps.deckeditor.cardeditor.TextFieldTile.populateList;

/**
 * Visits flashcards to return a respective form GUI Region. Users should use the form to input information in order to
 * create or edit flashcards.
 */
public class FormBuilderVisitor implements Visitor<Region> {

    private static final int DEFAULT_MCQ_ANSWER_COUNT = 4;
    private static final int DEFAULT_TABLE_ROWS = 4;
    private static final int DEFAULT_TABLE_COLUMNS = 2;
    private static final int MINIMUM_MCQ_ANSWER_COUNT = 1;
    private static final int MAXIMUM_MCQ_ANSWER_COUNT = 10;
    private static final int FORM_VERTICAL_SPACING = 15;
    private static final int MINIMUM_TABLE_ROWS = 1;
    private static final int MAXIMUM_TABLE_ROWS = 10;
    private static final int MINIMUM_TABLE_COLUMNS = 1;
    private static final int MAXIMUM_TABLE_COLUMNS = 10;

    private final CardEditorMvciModel model;

    public FormBuilderVisitor(CardEditorMvciModel model) {
        this.model = model;
    }

    /**
     * @param flashcard Flashcard to generate the form for.
     * @return Form for flashcard editing/creation.
     */
    @Override
    public Region visit(ClassicFlashcard flashcard) {
        TextFieldTile questionTile = new TextFieldTile(
                "Enter the Question",
                "Question to be displayed on the flashcard."
        );

        DirtyStringProperty questionProperty = new DirtyStringProperty(questionTile.getTextFields().get(0).getText());
        questionTile.getTextFields().get(0).textProperty().bindBidirectional(questionProperty);
        model.dirtyProperty().add(questionProperty);

        TextFieldTile answerTile = new TextFieldTile(
                "Enter the Answer",
                "Answer to be displayed on the other side."
        );

        DirtyStringProperty answerProperty = new DirtyStringProperty(answerTile.getTextFields().get(0).getText());
        answerTile.getTextFields().get(0).textProperty().bindBidirectional(answerProperty);
        model.dirtyProperty().add(answerProperty);

        return new VBox(FORM_VERTICAL_SPACING, questionTile, answerTile);
    }

    @Override
    public Region visit(MultipleChoiceFlashcard flashcard) {
        Tile questionTile = new Tile("Enter the Question", "Question to be displayed on the flashcard.");
        TextField questionInputField = new TextField();
        questionTile.setAction(questionInputField);

        Tile answerCountTile = new Tile("# Of Answers", "Enter the number of possible answers");
        Spinner<Integer> answerCountSpinner = new Spinner<>(MINIMUM_MCQ_ANSWER_COUNT, MAXIMUM_MCQ_ANSWER_COUNT, DEFAULT_MCQ_ANSWER_COUNT);
        answerCountTile.setAction(answerCountSpinner);

        VBox answerOptionBox = new VBox(FORM_VERTICAL_SPACING);

        Supplier<Node> answerOptionSupplier = () -> new TextFieldTileAnswerOption(
                "Answer Option",
                "Enter a possible answer.",
                false
        );

        populateList(answerOptionBox.getChildren(), answerCountSpinner.getValue(), answerOptionSupplier);
        answerCountSpinner.valueProperty().addListener((observable, oldCount, desiredCount) -> populateList(
                answerOptionBox.getChildren(),
                desiredCount,
                answerOptionSupplier
        ));

        return new VBox(15, questionTile, answerCountTile, answerOptionBox);
    }

    @Override
    public Region visit(TableFlashcard flashcard) {
        // I think that this should be bound to the flashcard's properties. Be

        TextFieldTile questionTile = new TextFieldTile(
                "Enter the Question",
                "Question to be displayed on the flashcard."
        );

        Tile rowCountTile = new Tile("# of Rows", "Enter the number of rows (possible answers)");
        Spinner<Integer> rowCountSpinner = new Spinner<>(MINIMUM_TABLE_ROWS, MAXIMUM_TABLE_ROWS, DEFAULT_TABLE_ROWS);
        rowCountTile.setAction(rowCountSpinner);

        Tile columnCountTile = new Tile("# of Columns", "Enter the number of rows (possible answers)");
        Spinner<Integer> columnCountSpinner = new Spinner<>(MINIMUM_TABLE_COLUMNS, MAXIMUM_TABLE_COLUMNS, DEFAULT_TABLE_COLUMNS);
        columnCountTile.setAction(columnCountSpinner);

        TextFieldTile headersTile = new TextFieldTile(
                "Table Headers",
                "Input the first row of the table."
        );
        headersTile.textFieldCountProperty().bind(columnCountSpinner.valueProperty());

        VBox tableRowInput = new VBox();

        Supplier<Node> tableAnswerSupplier = () -> {
            TextFieldTileAnswerOption tableAnswerOption = new TextFieldTileAnswerOption(
                    "Answer Option",
                    "Enter a possible answer.",
                    false
            );
            tableAnswerOption.textFieldCountProperty().bind(columnCountSpinner.valueProperty());
            return tableAnswerOption;
        };

        populateList(tableRowInput.getChildren(), rowCountSpinner.getValue(), tableAnswerSupplier);
        rowCountSpinner.valueProperty().addListener((observable, oldRowCount, desiredRowCount) -> populateList(
                tableRowInput.getChildren(),
                desiredRowCount,
                tableAnswerSupplier
        ));

        return new VBox(FORM_VERTICAL_SPACING, questionTile, rowCountTile, columnCountTile, headersTile, tableRowInput);
    }
}
