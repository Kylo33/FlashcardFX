package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import com.tobiasdiez.easybind.EasyBind;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.List;
import java.util.function.Consumer;

public class HomeMvciViewBuilder implements Builder<Region> {
    private HomeMvciModel model;
    private ObservableList<Node> menuCards;
    public HomeMvciViewBuilder(HomeMvciModel model, Consumer<Deck> practiceConsumer) {
        this.model = model;
        this.menuCards = createMenuCards(practiceConsumer);
    }

    private ObservableList<Node> createMenuCards(Consumer<Deck> practiceConsumer) {
        /*
         Must use mapBacked to avoid the "duplicate children added" exception.
         From: https://github.com/tobiasdiez/EasyBind, I want to have the "elements of the list [be]
         converted once and then stored in memory"
        */
        return EasyBind.mapBacked(model.getDecks(), deck -> {
            MenuCardBuilder card = new MenuCardBuilder(deck.getTitle(), deck.getDescription(), deck.getFlashcards().size());
            card.setAction(() -> {
                practiceConsumer.accept(deck);
            });
            return new Label(deck.getTitle());
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
