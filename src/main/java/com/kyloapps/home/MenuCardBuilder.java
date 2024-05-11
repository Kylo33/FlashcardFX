package com.kyloapps.home;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;


public class MenuCardBuilder implements Builder<Region> {
    private final ObjectProperty<EventHandler<ActionEvent>> actionEventObjectProperty = new SimpleObjectProperty<>();
    private final StringProperty title;
    private final StringProperty description;
    private final IntegerBinding cardCountBinding;

    public MenuCardBuilder(StringProperty title, StringProperty description, IntegerBinding cardCountBinding) {
        this.title = title;
        this.description = description;
        this.cardCountBinding = cardCountBinding;
    }

    @Override
    public Region build() {
        Card result = new Card();
        result.setHeader(getHeader());
        result.setFooter(getFooter());
        result.setMinWidth(200);
        result.setMaxWidth(200);
        result.setMinHeight(120);
        result.setMaxHeight(120);
        return result;
    }

    private Node getHeader() {
        Label titleLabel = new Label();
        titleLabel.textProperty().bind(title);
        titleLabel.getStyleClass().add(Styles.TITLE_4);

        Label descriptionLabel = new Label();
        descriptionLabel.textProperty().bind(description);
        descriptionLabel.getStyleClass().add(Styles.TEXT_MUTED);

        return new VBox(5, titleLabel, descriptionLabel);
    }

    private Region getFooter() {
        Button practiceButton = new Button("Practice", new FontIcon(MaterialDesignW.WEIGHT_LIFTER));
        practiceButton.setDefaultButton(true);
        practiceButton.getStyleClass().add(Styles.SMALL);
        practiceButton.onActionProperty().bind(actionEventObjectProperty);

        practiceButton.disableProperty().bind(Bindings.createBooleanBinding(() -> !(cardCountBinding.get() > 0), cardCountBinding));

        Label cardCountLabel = new Label(null, new FontIcon(MaterialDesignC.CARD_TEXT));
        cardCountLabel.textProperty().bind(cardCountBinding.asString());
        HBox result = new HBox(15, practiceButton, cardCountLabel);
        result.setAlignment(Pos.CENTER_LEFT);
        return result;
    }

    public void setAction(Runnable runnable) {
        actionEventObjectProperty.set((event) -> runnable.run());
    }
}
