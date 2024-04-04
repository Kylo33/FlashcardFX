package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.CardControllerVisitor;
import com.kyloapps.deckeditor.cardeditor.forms.classic.ClassicMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.table.TableMvciController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class CardTypeComboBoxFactory {
    private static final CardControllerVisitor<String> CARD_CONTROLLER_VISITOR = new CardControllerVisitor<>() {

        @Override
        public String visit(ClassicMvciController controller) {
            return "Classic";
        }

        @Override
        public String visit(MultipleChoiceMvciController controller) {
            return "Multiple Choice";
        }

        @Override
        public String visit(TableMvciController controller) {
            return "Table";
        }
    };

    private static final StringConverter<CardController<?>> controllerStringConverter = new StringConverter<>() {
        @Override
        public String toString(CardController<?> controller) {
            return controller == null ? "" : controller.accept(CARD_CONTROLLER_VISITOR);
        }

        @Override
        public CardController<?> fromString(String s) {
            return null;
        }
    };

    static ComboBox<CardController<?>> createCardTypeComboBox() {
        MultipleChoiceMvciController mcq = new MultipleChoiceMvciController();
        ComboBox<CardController<?>> controllerComboBox = new ComboBox<>(FXCollections.observableArrayList(
                mcq,
                new ClassicMvciController(),
                new TableMvciController()
        ));

        // TODO Clean up

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                controllerComboBox.setValue(mcq);
            });
        }).start();

        controllerComboBox.setConverter(controllerStringConverter);
        return controllerComboBox;
    }
}
