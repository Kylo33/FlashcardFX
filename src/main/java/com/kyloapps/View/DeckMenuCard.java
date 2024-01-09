package com.kyloapps.View;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.Deck;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.util.function.Consumer;

public class DeckMenuCard extends Card {

    public DeckMenuCard(Deck deck, Consumer<Deck> deckActionHandler) {
        // Dimensions
        setMinHeight(110);
        setMaxHeight(110);
        setMinWidth(200);
        setMaxWidth(200);

        // Create header
        Tile header = new Tile();
        setHeader(header);

        header.setTitle(deck.getTitle());

        String deckString;
        if (deck.getDescription().length() > 19) {
            deckString = deck.getDescription().substring(0, 20);
        } else {
            deckString = deck.getDescription();
        }

        header.setDescription(deckString + "...");

        // Create footer
        HBox footer = new HBox();
        setFooter(footer);

        Button practiceBtn = new Button("Practice");

        practiceBtn.setDisable(deck.getFlashcards().size() == 0);
        deck.getFlashcards().addListener((ListChangeListener<? super DisplayableFlashcard>) change -> practiceBtn.setDisable(deck.getFlashcards().size() == 0));

        Label countLabel = new Label();
        countLabel.textProperty().bind(Bindings.size(deck.getFlashcards()).asString());

        footer.getChildren().addAll(practiceBtn, countLabel);

        // Set the button's function
        practiceBtn.setOnAction((event) -> {
            deckActionHandler.accept(deck);
        });

        // Style footer
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setSpacing(4);

        practiceBtn.getStyleClass().add(Styles.SMALL);
        practiceBtn.setDefaultButton(true);

        countLabel.setGraphic(new FontIcon(MaterialDesignC.CARD_TEXT));
    }
}
