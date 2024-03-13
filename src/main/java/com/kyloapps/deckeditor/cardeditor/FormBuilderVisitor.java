package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.domain.*;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;

import java.util.ArrayList;
import java.util.List;
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
    private static final int MINIMUM_MCQ_ANSWER_COUNT = 0;
    private static final int MAXIMUM_MCQ_ANSWER_COUNT = 10;
    private static final int FORM_VERTICAL_SPACING = 15;
    private static final int MINIMUM_TABLE_ROWS = 1;
    private static final int MAXIMUM_TABLE_ROWS = 10;
    private static final int MINIMUM_TABLE_COLUMNS = 1;
    private static final int MAXIMUM_TABLE_COLUMNS = 10;

    private final CardEditorMvciModel model;
    // This list holds bindings of TextFieldTileAnswerOptions to prevent them from being garbage collected.
    private List<List<TextFieldTileAnswerOption>> preventGC = new ArrayList<>();

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

        VBox answerOptionBox = new VBox(FORM_VERTICAL_SPACING);

        ObservableList<AnswerOption<StringProperty>> flashcardOptions = flashcard.getOptions();

        // I want to fill up the answerOptionBox with new TextFieldTileAnswerOption s
        // They should be linked to a DirtyStringProperty (added to the model's CompositeDirtyProperty)
        // The corresponding option's StringProperty in the flashcard should be bound to the DirtyStringProperty

        ObservableList<TextFieldTileAnswerOption> optionForms = EasyBind.mapBacked(flashcardOptions, option -> {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption(
                "Answer Option",
                "Enter a possible answer."
            );

            DirtyBooleanProperty dirtyCorrectProperty = new DirtyBooleanProperty(option.isCorrect());
            result.correctProperty().bindBidirectional(dirtyCorrectProperty);
            model.getDirtyProperties().add(dirtyCorrectProperty);

            // some kind of error here when you remove answers

            DirtyStringProperty dirtyOptionProperty = new DirtyStringProperty(option.getContent().getValue());
            result.getTextFields().get(0).textProperty().bindBidirectional(dirtyOptionProperty);
            model.getDirtyProperties().add(dirtyOptionProperty);

            option.correctProperty().bind(dirtyCorrectProperty);
            option.getContent().bind(dirtyOptionProperty);

            return result;
        });
        preventGC.add(optionForms);

        Bindings.bindContent(answerOptionBox.getChildren(), optionForms);

        answerCountSpinner.valueProperty().addListener((observable, oldCount, desiredCount) -> {
            populateList(
                    flashcardOptions,
                    desiredCount,
                    () -> new AnswerOption<>(false, new SimpleStringProperty(""))
            );
        });

        return new VBox(15, questionTile, answerCountTile, answerOptionBox);
    }

    private TextFieldTile getSimpleInputTile(String title, String description, StringProperty flashcardQuestionProperty) {
        TextFieldTile questionTile = new TextFieldTile(
                title,
                description
        );

        DirtyStringProperty dirtyQuestionProperty = new DirtyStringProperty(flashcardQuestionProperty.get());
        questionTile.getTextFields().get(0).textProperty().bindBidirectional(dirtyQuestionProperty);
        model.getDirtyProperties().add(dirtyQuestionProperty);

        flashcardQuestionProperty.bind(dirtyQuestionProperty);
        return questionTile;
    }

    @Override
    public Region visit(TableFlashcard flashcard) {
        return null;
//        TextFieldTile questionTile = new TextFieldTile(
//                "Enter the Question",
//                "Question to be displayed on the flashcard."
//        );
//
//        Tile rowCountTile = new Tile("# of Rows", "Enter the number of rows (possible answers)");
//        Spinner<Integer> rowCountSpinner = new Spinner<>(MINIMUM_TABLE_ROWS, MAXIMUM_TABLE_ROWS, DEFAULT_TABLE_ROWS);
//        rowCountTile.setAction(rowCountSpinner);
//
//        Tile columnCountTile = new Tile("# of Columns", "Enter the number of rows (possible answers)");
//        Spinner<Integer> columnCountSpinner = new Spinner<>(MINIMUM_TABLE_COLUMNS, MAXIMUM_TABLE_COLUMNS, DEFAULT_TABLE_COLUMNS);
//        columnCountTile.setAction(columnCountSpinner);
//
//        TextFieldTile headersTile = new TextFieldTile(
//                "Table Headers",
//                "Input the first row of the table."
//        );
//        headersTile.textFieldCountProperty().bind(columnCountSpinner.valueProperty());
//
//        VBox tableRowInput = new VBox();
//
//        Supplier<Node> tableAnswerSupplier = () -> {
//            TextFieldTileAnswerOption tableAnswerOption = new TextFieldTileAnswerOption(
//                    "Answer Option",
//                    "Enter a possible answer.",
//                    false
//            );
//            tableAnswerOption.textFieldCountProperty().bind(columnCountSpinner.valueProperty());
//            return tableAnswerOption;
//        };
//
//        populateList(tableRowInput.getChildren(), rowCountSpinner.getValue(), tableAnswerSupplier);
//        rowCountSpinner.valueProperty().addListener((observable, oldRowCount, desiredRowCount) -> populateList(
//                tableRowInput.getChildren(),
//                desiredRowCount,
//                tableAnswerSupplier
//        ));
//
//        return new VBox(FORM_VERTICAL_SPACING, questionTile, rowCountTile, columnCountTile, headersTile, tableRowInput);
    }
}
