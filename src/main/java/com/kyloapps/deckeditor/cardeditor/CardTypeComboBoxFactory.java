package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.domain.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class CardTypeComboBoxFactory {
    private static final ControllerVisitor<String> controllerVisitor = new ControllerVisitor<>() {
        @Override
        public String visit(MultipleChoiceMvciController controller) {
            return "Multiple Choice";
        }
    };

    private static final StringConverter<CardController<?>> controllerStringConverter = new StringConverter<>() {
        @Override
        public String toString(CardController<?> controller) {
            return controller == null ? "" : controller.accept(controllerVisitor);
        }

        @Override
        public CardController<?> fromString(String s) {
            return null;
        }
    };

    static ComboBox<CardController<?>> createCardTypeComboBox() {
        ComboBox<CardController<?>> controllerComboBox = new ComboBox<>(FXCollections.observableArrayList(
                new MultipleChoiceMvciController()
        ));
        controllerComboBox.setConverter(controllerStringConverter);
        return controllerComboBox;
    }
}
