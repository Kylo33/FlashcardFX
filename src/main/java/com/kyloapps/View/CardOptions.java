package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class CardOptions extends VBox {
    private QuestionCreator creator;
    private ChoiceBox<String> choiceBox;

    public CardOptions() {
        Tile type = new Tile("Type", "Select the type of flashcard.");
        choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(SimpleFlashcardView.typeString,
                MultipleChoiceFlashcardView.typeString, TableFlashcardView.typeString));
        type.setAction(choiceBox);
        getChildren().addAll(type);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            creator = QuestionCreatorFactory.build(newValue);
            if (getChildren().size() > 1) {
                getChildren().set(1, creator.get());
            } else {
                getChildren().add(creator.get());
            }
        });
    }

    public CardOptions(String typeString) {
        this();
        choiceBox.setValue(typeString);
    }

    public QuestionCreator getCreator() {
        return creator;
    }
}
