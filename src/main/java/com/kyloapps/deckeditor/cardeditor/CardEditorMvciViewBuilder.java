package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.domain.Flashcard;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class CardEditorMvciViewBuilder implements Builder<Region> {

    private final CardEditorMvciModel model;

    public CardEditorMvciViewBuilder(CardEditorMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        return new VBox(15, createTypeTile(), createControllerViewBox());
    }

    private Region createControllerViewBox() {
        VBox result = new VBox();
        if (model.getController() != null) {
            result.getChildren().add(model.getController().getView());
        }
        model.controllerProperty().addListener((observableValue, oldController, newController) -> {
            result.getChildren().setAll(newController.getView());
        });
        return result;
    }

    private Node createTypeTile() {
        Tile typeTile = new Tile("Select Flashcard Type", "What kind of flashcard do you want to create?");
        ComboBox<CardController<?>> cardTypeComboBox = CardTypeComboBoxFactory.createCardTypeComboBox();
        cardTypeComboBox.valueProperty().bindBidirectional(model.controllerProperty());
        typeTile.setAction(cardTypeComboBox);
        return typeTile;
    }
}
