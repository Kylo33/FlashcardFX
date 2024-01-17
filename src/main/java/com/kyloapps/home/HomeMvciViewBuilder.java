package com.kyloapps.home;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

import java.awt.*;

public class HomeMvciViewBuilder implements Builder<Region> {
    private HomeMvciModel model;
    private ObservableList<Node> menuCards;
    public HomeMvciViewBuilder(HomeMvciModel model) {
        this.model = model;
        this.menuCards = createMenuCards();
    }

    private ObservableList<Node> createMenuCards() {
        return EasyBind.map(model.getDecks(), deck -> {
            MenuCard card = new MenuCard(deck.getTitle(), deck.getDescription(), deck.getFlashcards().size());
            return card.build();
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
