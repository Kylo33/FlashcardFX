package com.kyloapps.home;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

public class MenuCard implements Builder<Region> {
    private final String title;
    private final String description;
    private final Integer cardCount;
    public MenuCard(String title, String description, int cardCount) {
        this.title = title;
        this.description = description;
        this.cardCount = cardCount;
    }

    @Override
    public Region build() {
        Card result = new Card();
        result.setHeader(new Tile(title, description));
        result.setFooter(getFooter());
        result.setMinWidth(200);
        result.setMaxWidth(200);
        result.setMinHeight(120);
        result.setMaxHeight(120);
        return result;
    }

    private Region getFooter() {
        Button practiceButton = new Button("Practice", new FontIcon(MaterialDesignW.WEIGHT_LIFTER));
        practiceButton.setDefaultButton(true);
        practiceButton.getStyleClass().add(Styles.SMALL);
        Label cardCountLabel = new Label(cardCount.toString(), new FontIcon(MaterialDesignC.CARD_TEXT));
        HBox result = new HBox(15, practiceButton, cardCountLabel);
        result.setAlignment(Pos.CENTER_LEFT);
        return result;
    }
}
