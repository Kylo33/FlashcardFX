package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.domain.Flashcard;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class CardEditorMvciViewBuilder implements Builder<Region> {

    private final CardEditorMvciModel model;
    private final FormBuilderVisitor formBuilderVisitor;
    private final Runnable changeCardTypeAction;
    private final Node formContainer;

    public CardEditorMvciViewBuilder(CardEditorMvciModel model, Runnable changeCardTypeAction) {
        this.model = model;
        this.changeCardTypeAction = changeCardTypeAction;
        formBuilderVisitor = new FormBuilderVisitor(model);
        formContainer = createCardFields();
    }

    @Override
    public Region build() {
        VBox result = new VBox(15);
        result.getChildren().addAll(createTypeTile(), formContainer);
        return result;
    }

    private Node createTypeTile() {
        Tile typeTile = new Tile("Select Flashcard Type", "What kind of flashcard do you want to create?");
        ComboBox<Flashcard> cardTypeComboBox = CardTypeComboBoxFactory.createCardTypeComboBox(changeCardTypeAction);
        cardTypeComboBox.valueProperty().bindBidirectional(model.flashcardProperty());
        typeTile.setAction(cardTypeComboBox);
        return typeTile;
    }


    private Node createCardFields() {
        VBox container = new VBox();
        model.flashcardProperty().addListener((observableValue, oldFlashcard, newFlashcard) -> {
            Node cardFields = newFlashcard.accept(formBuilderVisitor);
            container.getChildren().add(cardFields);
            if (container.getChildren().size() > 1) {
                container.getChildren().remove(0);
            }
        });
        return container;
    }
}
