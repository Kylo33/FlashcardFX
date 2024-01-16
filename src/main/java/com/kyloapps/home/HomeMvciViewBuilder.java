package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeMvciViewBuilder implements Builder<Region> {
    private ObservableList<Node> representationOfDecks = FXCollections.observableArrayList();
    public HomeMvciViewBuilder(HomeMvciModel model) {
        model.getDecks().addListener((ListChangeListener<? super Deck>) change -> {
            this.representationOfDecks.setAll(change.getList().stream().map(deck -> new Label(deck.getTitle())).collect(Collectors.toList()));
        });
    }

    @Override
    public Region build() {
        FlowPane result = new FlowPane();
        Bindings.bindContent(result.getChildren(), representationOfDecks);
        return result;
    }
}
