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

    private static final StringConverter<Flashcard> flashcardStringConverter = new StringConverter<>() {
        @Override
        public String toString(Flashcard flashcard) {
            return flashcard == null ? "" : flashcard.accept(cardTypeVisitor);
        }

        @Override
        public Flashcard fromString(String s) {
            return null;
        }
    };

    static ComboBox<Flashcard> createCardTypeComboBox(Runnable action) {
        ComboBox<Flashcard> flashcardComboBox = new ComboBox<>(FXCollections.observableArrayList(
                new ClassicFlashcard(),
                new MultipleChoiceFlashcard(),
                new TableFlashcard()
        ));
        flashcardComboBox.valueProperty().addListener((observableValue, oldFlashcard, newFlashcard) -> {
            if (oldFlashcard != null)
                action.run();
        });
        flashcardComboBox.setConverter(flashcardStringConverter);
        return flashcardComboBox;
    }
}
