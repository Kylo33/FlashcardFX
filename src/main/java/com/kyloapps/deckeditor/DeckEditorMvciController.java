package com.kyloapps.deckeditor;

import com.kyloapps.domain.Deck;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Region;

import java.io.File;
import java.util.function.Supplier;

public class DeckEditorMvciController {
    private final DeckEditorMvciViewBuilder viewBuilder;

    public DeckEditorMvciController(ObservableList<Deck> deckListProperty, ObservableMap<Deck, File> deckFileMap, ObjectProperty<File> currentDirectoryProperty) {
        DeckEditorMvciModel model = new DeckEditorMvciModel();
        DeckEditorMvciInteractor interactor = new DeckEditorMvciInteractor(model);
        viewBuilder = new DeckEditorMvciViewBuilder(
                model,
                interactor::createDeck,
                interactor::deleteDeck,
                interactor::createCardEditor,
                interactor::saveChanges,
                interactor::revertChanges,
                interactor::deleteCard,
                interactor::switchDecks
        );
        Bindings.bindContentBidirectional(model.getDecks(), deckListProperty);
        Bindings.bindContentBidirectional(model.getDeckFileMap(), deckFileMap);
        model.currentDirectoryProperty().bind(currentDirectoryProperty);
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
