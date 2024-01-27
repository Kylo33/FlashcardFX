package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
        return EasyBind.map(model.getDecks(), deck -> {
            MenuCard card = new MenuCard(deck.getTitle(), deck.getDescription(), deck.getFlashcards().size());
            card.setAction(() -> {
                practiceConsumer.accept(deck);
            });
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
        menuCards.addListener((ListChangeListener<? super Node>) change -> {
            if (change.getList().isEmpty()) {
                content.getChildren().clear();
                return;
            }
            content.getChildren().setAll(change.getList());
        });
        return content;
    }

    private Region createRoot(Node content) {
        ScrollPane result = new ScrollPane(content);
        result.setPadding(new Insets(30));
        return result;
    }
}
