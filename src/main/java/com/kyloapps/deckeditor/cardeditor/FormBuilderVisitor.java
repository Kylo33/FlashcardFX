package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import com.kyloapps.domain.Visitor;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

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


    private static final int FORM_VERTICAL_SPACING = 15;

    private final CardEditorMvciModel model;
    // This list holds bindings of TextFieldTileAnswerOptions to prevent them from being garbage collected.
    private final List<List<TextFieldTileAnswerOption>> preventGC = new ArrayList<>();

    // The problem is: When TextFieldTiles are created, they are bound to DirtyStringProperties. However, when they are removed
    // (like in MCQ flashcards), their DirtyStringProperty is not removed. They will be removed if the deck is reverted, but not if it is saved.
    // To solve this, I am going to maintain a list of the TFTs and a mapBacked list of their corresponding dirty properties.
    // When the options are removed, their DirtyProperties will be too. I think that the list of TFTs will be the dirty list, the other one will be normal, but
    // Its contents must be added to the main composite dirty property when it is requested. This won't affect anything because
    // if the "number"  of options changes, the whole thing will already be dirty.

    //TODO if all cards are deleted, it won't let you save.

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
        return null;
//        TextFieldTile questionTile = getSimpleInputTile(
//                "Enter the Question",
//                "Question to be displayed on the flashcard.",
//                flashcard.questionProperty()
//        );
//
//        Tile answerCountTile = new Tile("# Of Answers", "Enter the number of possible answers");
//        Spinner<Integer> answerCountSpinner = new Spinner<>(MINIMUM_MCQ_ANSWER_COUNT, MAXIMUM_MCQ_ANSWER_COUNT, flashcard.getOptions().size());
//        answerCountTile.setAction(answerCountSpinner);
//
//        VBox answerOptionBox = new VBox(FORM_VERTICAL_SPACING);
//
//        ObservableList<AnswerOption<StringProperty>> flashcardOptions = flashcard.getOptions();
//
//        // I want to fill up the answerOptionBox with new TextFieldTileAnswerOption s
//        // They should be linked to a DirtyStringProperty (added to the model's CompositeDirtyProperty)
//        // The corresponding option's StringProperty in the flashcard should be bound to the DirtyStringProperty
//
//        ObservableList<TextFieldTileAnswerOption> optionForms = EasyBind.mapBacked(flashcardOptions, option -> {
//            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption(
//                "Answer Option",
//                "Enter a possible answer."
//            );
//
//            DirtyBooleanProperty dirtyCorrectProperty = new DirtyBooleanProperty(option.isCorrect());
//            result.correctProperty().bindBidirectional(dirtyCorrectProperty);
//            model.getDirtyProperties().add(dirtyCorrectProperty);
//
//            DirtyStringProperty dirtyOptionProperty = new DirtyStringProperty(option.getContent().getValue());
//            result.getTextFields().get(0).textProperty().bindBidirectional(dirtyOptionProperty);
//            model.getDirtyProperties().add(dirtyOptionProperty);
//
//            option.correctProperty().bind(dirtyCorrectProperty);
//            option.getContent().bind(dirtyOptionProperty);
//
//            return result;
//        });
//        preventGC.add(optionForms);
//
//        Bindings.bindContent(answerOptionBox.getChildren(), optionForms);
//
//        answerCountSpinner.valueProperty().addListener((observable, oldCount, desiredCount) -> {
//            populateList(
//                    flashcardOptions,
//                    desiredCount,
//                    () -> new AnswerOption<>(false, new SimpleStringProperty(""))
//            );
//        });
//
//        return new VBox(15, questionTile, answerCountTile, answerOptionBox);
    }

    private TextFieldTile getSimpleInputTile(String title, String description, StringProperty flashcardQuestionProperty) {
        TextFieldTile questionTile = new TextFieldTile(title, description);
        model.getTextFieldTiles().add(questionTile);
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
