package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

/*
    This class and its implementation were primarily designed by me. However, I did get feedback from ChatGPT (3.5).
 */
public class CardTypeComboBoxFactory {
    private static final Visitor<String> cardTypeVisitor = new Visitor<>() {
        @Override
        public String visit(ClassicFlashcard flashcard) {
            return "Classic (Front & Back)";
        }

        @Override
        public String visit(MultipleChoiceFlashcard flashcard) {
            return "Multiple Choice";
        }

        @Override
        public String visit(TableFlashcard flashcard) {
            return "Table";
        }
    };

    private static final StringConverter<Flashcard> flashcardStringConverter = new StringConverter<Flashcard>() {
        @Override
        public String toString(Flashcard flashcard) {
            return flashcard == null ? "" : flashcard.accept(cardTypeVisitor);
        }

        @Override
        public Flashcard fromString(String s) {
            return null;
        }
    };

    //TODO move away, this is not just view.
    private static final Visitor<Void> cardResetVisitor = new Visitor<>() {
        @Override
        public Void visit(ClassicFlashcard flashcard) {
            flashcard.questionProperty().unbind();
            flashcard.setQuestion("");
            flashcard.answerProperty().unbind();
            flashcard.setAnswer("");
            return null;
        }

        @Override
        public Void visit(MultipleChoiceFlashcard flashcard) {
            flashcard.questionProperty().unbind();
            flashcard.setQuestion("");
            flashcard.getOptions().clear();
            return null;
        }

        @Override
        public Void visit(TableFlashcard flashcard) {
            flashcard.setOptions("");
            flashcard.getHeaders().clear();
            flashcard.getOptions().clear();
            return null;
        }
    };

    static ComboBox<Flashcard> createCardTypeComboBox() {
        ComboBox<Flashcard> flashcardComboBox = new ComboBox<>(FXCollections.observableArrayList(
                new ClassicFlashcard(),
                new MultipleChoiceFlashcard(),
                new TableFlashcard()
        ));
        flashcardComboBox.valueProperty().addListener((observableValue, oldFlashcard, newFlashcard) -> {
            if (oldFlashcard != null)
                oldFlashcard.accept(cardResetVisitor);
        });
        flashcardComboBox.setConverter(flashcardStringConverter);
        return flashcardComboBox;
    }
}
