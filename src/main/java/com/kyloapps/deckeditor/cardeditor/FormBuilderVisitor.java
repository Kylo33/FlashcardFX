package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.domain.*;
import com.tobiasdiez.easybind.EasyBind;
import com.tobiasdiez.easybind.EasyObservableList;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Visits flashcards to return a respective form GUI Region. Users should use the form to input information in order to
 * create or edit flashcards.
 */
public class FormBuilderVisitor implements Visitor<Region> {

    private static final int MINIMUM_MCQ_ANSWER_COUNT = 0;
    private static final int MAXIMUM_MCQ_ANSWER_COUNT = 10;
    private static final int MINIMUM_TABLE_ROW_COUNT = 0;
    private static final int MAXIMUM_TABLE_ROW_COUNT = 10;
    private static final int MINIMUM_TABLE_COLUMN_COUNT = 1;
    private static final int MAXIMUM_TABLE_COLUMN_COUNT = 5;

    public static final int FORM_VERTICAL_SPACING = 15;

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
        TextFieldTile questionTile = getSimpleInputTile(
                "Enter the Question",
                "Question to be displayed on the flashcard.",
                flashcard.questionProperty()
        );

        TextFieldTile answerTile = getSimpleInputTile(
                "Enter the Answer",
                "Answer to be displayed on the other side.",
                flashcard.answerProperty()
        );

        return new VBox(FORM_VERTICAL_SPACING, questionTile, answerTile);
    }

    @Override
    public Region visit(MultipleChoiceFlashcard flashcard) {
        TextFieldTile questionTile = getSimpleInputTile(
                "Enter the Question",
                "Question to be displayed on the flashcard.",
                flashcard.questionProperty()
        );

        Tile answerCountTile = new Tile("# Of Answers", "Enter the number of possible answers");
        Spinner<Integer> answerCountSpinner = new Spinner<>(MINIMUM_MCQ_ANSWER_COUNT, MAXIMUM_MCQ_ANSWER_COUNT, flashcard.getOptions().size());
        answerCountTile.setAction(answerCountSpinner);

        // Populate the tile's answer options
        ListModifications.populateList(flashcard.getOptions(),
                answerCountSpinner.getValue(),
                () -> new AnswerOption<>(false, new SimpleStringProperty("")));
        answerCountSpinner.valueProperty().addListener(
                (observable, oldCount, newCount)
                        -> ListModifications.populateList(flashcard.getOptions(), newCount,
                        () -> new AnswerOption<>(false, new SimpleStringProperty(""))));

        VBox answerOptionBox = new VBox(FORM_VERTICAL_SPACING);

        // Populate the answerOptionBox

        EasyObservableList<TextFieldTileAnswerOption> mapped = EasyBind.mapBacked(flashcard.getOptions(), option -> {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption("Answer Option", "Enter a possible answer.");
            result.getTextFields().get(0).setText(option.getContent().get());
            result.setCorrect(option.isCorrect());
            return result;
        });
        Bindings.bindContent(answerOptionBox.getChildren(), mapped);

        // Prevent mapped from being garbage collected (need better solution)

        answerOptionBox.getChildren().addListener((InvalidationListener) change -> {
            var m = mapped;
        });

        // Listen for changes to model.getTextFieldTiles()

        model.getTextFieldTileLists().addListener((ListChangeListener<? super TextFieldTile>) change -> {
            change.next();
            if (change.wasAdded())
                answerOptionBox.getChildren().addAll(change.)
        });

        return new VBox(15, questionTile, answerCountTile, answerOptionBox);
    }

    private TextFieldTile getSimpleInputTile(String title, String description, StringProperty flashcardQuestionProperty) {
        TextFieldTile questionTile = new TextFieldTile(title, description);
        model.getTextFieldTileLists().add(questionTile);
        questionTile.getTextFields().get(0).textProperty().bindBidirectional(flashcardQuestionProperty);
        return questionTile;
    }

    @Override
    public Region visit(TableFlashcard flashcard) {
        TextFieldTile questionTile = getSimpleInputTile(
                "Enter the Question",
                "Question to be displayed on the flashcard.",
                flashcard.questionProperty()
        );

        Tile rowCountTile = new Tile("# Of Rows", "Enter the number of rows.");
        Spinner<Integer> rowCountSpinner = new Spinner<>(MINIMUM_TABLE_ROW_COUNT, MAXIMUM_TABLE_ROW_COUNT, flashcard.getOptions().size());
        rowCountTile.setAction(rowCountSpinner);

        Tile columnCountTile = new Tile("# Of Columns", "Enter the number of possible answers");
        Spinner<Integer> columnCountSpinner = new Spinner<>(MINIMUM_TABLE_COLUMN_COUNT, MAXIMUM_TABLE_COLUMN_COUNT, MINIMUM_TABLE_COLUMN_COUNT);
        columnCountTile.setAction(columnCountSpinner);

        VBox answerOptionBox = new VBox(FORM_VERTICAL_SPACING);
        return questionTile;
    }
}
