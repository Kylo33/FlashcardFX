package com.kyloapps.practice;

import atlantafx.base.theme.Styles;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.FlashcardVisitor;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerCreatorVisitor implements FlashcardVisitor<AnswerDisplay> {
    @Override
    public AnswerDisplay visit(ClassicFlashcard flashcard) {
        Label answer = new Label(flashcard.getAnswer());
        answer.setTextAlignment(TextAlignment.CENTER);
        answer.setWrapText(true);
        answer.setVisible(false);

        Runnable forwardAction = () -> answer.setVisible(true);
        Runnable backwardsAction = () -> answer.setVisible(false);

        return new AnswerDisplay(answer, forwardAction, backwardsAction);
    }

    @Override
    public AnswerDisplay visit(MultipleChoiceFlashcard flashcard) {
        IntegerProperty state = new SimpleIntegerProperty(0);

        VBox answers = new VBox(5);
        answers.setAlignment(Pos.CENTER_LEFT);
        List<Label> correctAnswers = new ArrayList<>();
        answers.getChildren().addAll(flashcard.getOptions().stream().map(stringPropertyAnswerOption -> {
            Label l = new Label(stringPropertyAnswerOption.getContent().get(), new FontIcon(MaterialDesignC.CHECKBOX_BLANK_CIRCLE));
            l.getStyleClass().add("mcq-option");
            if (stringPropertyAnswerOption.isCorrect()) correctAnswers.add(l);
            return l;
        }).collect(Collectors.toList()));
        answers.visibleProperty().bind(Bindings.createBooleanBinding(() -> state.get() > 0, state));

        correctAnswers.forEach(label
                -> {
            label.graphicProperty().bind(
                    Bindings.createObjectBinding(()
                                    -> state.get() == 2
                                    ? new FontIcon(MaterialDesignC.CHECKBOX_MARKED_CIRCLE)
                                    : new FontIcon(MaterialDesignC.CHECKBOX_BLANK_CIRCLE),
                            state));
            label.styleProperty().bind(Bindings.createStringBinding(() -> {
                return state.get() == 2 ? "-fx-background-color: -color-success-muted;" : "";
            }, state));
        });

        Runnable forwardAction = () -> {
            if (state.get() < 2)
                state.set(state.get() + 1);
        };

        Runnable backwardAction = () -> {
            if (state.get() > 0)
                state.set(state.get() - 1);
        };

        return new AnswerDisplay(answers, forwardAction, backwardAction);
    }

    @Override
    public AnswerDisplay visit(TableFlashcard flashcard) {
        BooleanProperty answersShowing = new SimpleBooleanProperty(false);

        GridPane table = new GridPane();
        List<Node> correctNodes = new ArrayList<>();

        int columns = flashcard.getHeaders().size();

        for (int i = 0; i < columns; i++) {
            Label l = new Label(flashcard.getHeaders().get(i).get());
            l.getStyleClass().add(Styles.TEXT_BOLD);
            table.add(l, i, 0);
        }

        for (int i = 0; i < flashcard.getOptions().size(); i++)
            for (int j = 0; j < columns; j++) {
                TextFlow t = new TextFlow(new Text(flashcard.getOptions().get(i).getContent().get(j).get()));
                table.add(t, j, i + 1);
                if (flashcard.getOptions().get(i).isCorrect())
                    correctNodes.add(t);
            }

        table.getChildren().forEach(child -> {
            child.getStyleClass().add("table-cell");
            GridPane.setFillWidth(child, true);
            ((Region) child).setMaxWidth(Double.MAX_VALUE);
        });
        correctNodes.forEach(node -> node.styleProperty().bind(Bindings.createStringBinding(() -> {
            return answersShowing.get() ? "-fx-background-color: -color-success-muted;" : "";
        }, answersShowing)));

        Runnable forwardAction = () -> answersShowing.set(true);
        Runnable backwardAction = () -> answersShowing.set(false);

        return new AnswerDisplay(table, forwardAction, backwardAction);
    }
}
