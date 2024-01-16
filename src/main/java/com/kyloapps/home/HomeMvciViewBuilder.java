package com.kyloapps.home;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class HomeMvciViewBuilder implements Builder<Region> {
    private HomeMvciModel model;
    private ObservableList<Node> menuCards;
    public HomeMvciViewBuilder(HomeMvciModel model) {
        this.model = model;
        this.menuCards = createMenuCards();
    }

    private ObservableList<Node> createMenuCards() {
        return EasyBind.map(model.getDecks(), deck -> {
            Card newMenuCard = new Card();
            newMenuCard.setHeader(new Tile(deck.getTitle(), deck.getDescription()));
            newMenuCard.getStyleClass().add("menu-card");
            newMenuCard.setPrefHeight(110);
            newMenuCard.setPrefWidth(200);
            return newMenuCard;
        });
    }

    @Override
    public Region build() {
        Region content = createContent();
        Region result = createRoot(content);
        return result;
    }

    private Region createContent() {
        FlowPane content = new FlowPane();
        content.setHgap(15);
        content.setVgap(15);
        Bindings.bindContent(content.getChildren(), menuCards);
        return content;
    }

    private Region createRoot(Node content) {
        ScrollPane result = new ScrollPane(content);
        result.setPadding(new Insets(30));
        return result;
    }
}
