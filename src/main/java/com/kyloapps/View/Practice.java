package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import com.kyloapps.Model.PracticeDeckManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class Practice extends BorderPane {
    private StackPane card = new StackPane();
    private final PracticeDeckManager practiceDeckManager;
    private Button prevBtn;
    private Button nextBtn;

    public Practice(PracticeDeckManager practiceDeckManager) {
        this.practiceDeckManager = practiceDeckManager;

        // Apply CSS styles.
        card.setPadding(new Insets(30));
        card.getStyleClass().add("flashcard");

        // Bring the card to the center and add some padding around it.
        setCenter(card);
        BorderPane.setMargin(card, new Insets(30));

        // Add navigation buttons to the bottom of the screen (prev/next card)
        setBottom(getNavButtons());
    }

    public StackPane getCardContainer() {
        return card;
    }

    private Node getNavButtons() {
        // Create a HBox container.
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        // Add two buttons to it.
        prevBtn = new Button();
        nextBtn = new Button();
        hBox.getChildren().addAll(prevBtn, nextBtn);

        // Add icons and style them to look connected.
        prevBtn.getStyleClass().addAll(Styles.LEFT_PILL, Styles.LARGE);
        prevBtn.setGraphic(new FontIcon(MaterialDesignC.CHEVRON_LEFT));

        nextBtn.getStyleClass().addAll(Styles.RIGHT_PILL, Styles.LARGE);
        nextBtn.setGraphic(new FontIcon(MaterialDesignC.CHEVRON_RIGHT));

        // Disable pressing with keyboard
        prevBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);

        return hBox;
    }

    public Button getPrevBtn() {
        return prevBtn;
    }

    public Button getNextBtn() {
        return nextBtn;
    }
}
