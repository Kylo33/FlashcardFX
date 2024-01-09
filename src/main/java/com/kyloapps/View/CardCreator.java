package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import com.kyloapps.DisplayableFlashcard;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardCreator extends VBox {
    private VBox cardOptionBox;
    private Button addButton;

    public CardCreator() {
        setPadding(new Insets(15));
        HBox addButtonRegion = getAddButton();
        cardOptionBox = new VBox();
        getChildren().addAll(cardOptionBox, addButtonRegion);

        addButton = (Button) addButtonRegion.getChildren().get(0);

        addButton.setOnAction((event) -> {
            CardOptions cardOptions = new CardOptions();
            Platform.runLater(() -> {
                addCardOptions(cardOptions);
            });
        });
    }

    private HBox getAddButton() {
        HBox region = new HBox();
        Button addButton = new Button("Add Card", new FontIcon(MaterialDesignP.PLUS));
        addButton.getStyleClass().addAll(Styles.BUTTON_OUTLINED);
        addButton.setDefaultButton(true);
        region.getChildren().add(addButton);
        region.setAlignment(Pos.CENTER);
        return region;
    }

    public void addCardOptions(CardOptions cardOptions) {
        cardOptionBox.getChildren().addAll(cardOptions, new Separator(Orientation.HORIZONTAL));
    }

    public void clearCardOptions() {
        cardOptionBox.getChildren().clear();
    }
}
