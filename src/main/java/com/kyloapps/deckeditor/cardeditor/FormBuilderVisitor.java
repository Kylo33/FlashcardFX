package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import com.kyloapps.domain.Visitor;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import javax.security.auth.callback.Callback;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FormBuilderVisitor implements Visitor<Region> {

    public static final int DEFAULT_MCQ_ANSWER_COUNT = 4;
    public static final int DEFAULT_TABLE_ROWS = 4;
    public static final int DEFAULT_TABLE_COLUMNS = 2;

    @Override
    public Region visit(ClassicFlashcard flashcard) {
        Tile questionTile = new Tile("Enter the Question", "Question to be displayed on the flashcard.");
        TextField questionInputField = new TextField();
        questionTile.setAction(questionInputField);

        Tile answerTile = new Tile("Enter the Answer", "Answer to be displayed on the other side.");
        TextField answerInputField = new TextField();
        answerTile.setAction(answerInputField);
        return new VBox(15, questionTile, answerTile);
    }

    @Override
    public Region visit(MultipleChoiceFlashcard flashcard) {
        Tile questionTile = new Tile("Enter the Question", "Question to be displayed on the flashcard.");
        TextField questionInputField = new TextField();
        questionTile.setAction(questionInputField);

        Tile answerCountTile = new Tile("# Of Answers", "Enter the number of possible answers");
        Spinner<Integer> answerCountSpinner = new Spinner<>(1, 10, DEFAULT_MCQ_ANSWER_COUNT);
        answerCountTile.setAction(answerCountSpinner);

        VBox answerBox = new VBox(15);
//        FormBuilderVisitor.shrinkOrGrowToSize(
//                answerBox.getChildren().size(),
//                DEFAULT_MCQ_ANSWER_COUNT,
//                () -> removeMultipleChoiceAnswer(answerBox),
//                () -> addMultipleChoiceAnswer(answerBox)
//        );
//        answerCountSpinner.valueProperty().addListener((observable, oldAnswerCount, newAnswerCount) -> {
//            FormBuilderVisitor.shrinkOrGrowToSize(
//                    oldAnswerCount,
//                    newAnswerCount,
//                    () -> removeMultipleChoiceAnswer(answerBox),
//                    () -> addMultipleChoiceAnswer(answerBox)
//            );
//        });

        return new VBox(15, questionTile, answerCountTile, answerBox);
    }

    private void removeMultipleChoiceAnswer(Pane answerBox) {
        answerBox.getChildren().remove(answerBox.getChildren().size() - 1);
    }

    private void addMultipleChoiceAnswer(Pane answerBox) {
        Tile result = new Tile("Answer Choice", "Enter text to be displayed as an answer to the question.");

        ToggleButton correctSwitch = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        correctSwitch.getStyleClass().addAll(Styles.BUTTON_ICON);

        TextField optionContent = new TextField();

        result.setAction(new HBox(10, optionContent, correctSwitch));

        answerBox.getChildren().add(result);
    }

    @Override
    public Region visit(TableFlashcard flashcard) {
        Tile questionTile = new Tile("Enter the Question", "Question to be displayed on the flashcard.");
        TextField questionInputField = new TextField();
        questionTile.setAction(questionInputField);

        Tile rowCountTile = new Tile("# of Rows", "Enter the number of rows (possible answers)");
        Spinner<Integer> rowCountSpinner = new Spinner<>(1, 10, DEFAULT_TABLE_ROWS);
        rowCountTile.setAction(rowCountSpinner);

        Tile columnCountTile = new Tile("# of Columns", "Enter the number of rows (possible answers)");
        Spinner<Integer> columnCountSpinner = new Spinner<>(1, 10, DEFAULT_TABLE_COLUMNS);
        columnCountTile.setAction(columnCountSpinner);

        VBox tableRowInputList = new VBox();

        // I need to bind the value of the row count spinner to the number of elements in the table row input vbox
        rowCountSpinner.valueProperty().addListener((observable, oldRowCount, desiredRowCount) -> {
            populate(tableRowInputList.getChildren(), desiredRowCount, () -> new TableRowInput(columnCountSpinner.valueProperty(), "Answer Option", "Enter a possible answer."));
        });

        return new VBox(15, questionTile, rowCountTile, columnCountTile, tableRowInputList);
    }

    public static <T> void populate(List<T> list, int desiredCount, Supplier<T> nodeSupplier) {
        int currentCount = list.size();
        if (desiredCount > currentCount) {
            for (int i = 0, c = desiredCount - currentCount; i < c; i++)
                list.add(nodeSupplier.get());
        } else if (desiredCount < currentCount) {
            for (int i = 0, c = currentCount - desiredCount; i < c; i++)
                list.remove(list.size() - 1);
        }
    }
}
