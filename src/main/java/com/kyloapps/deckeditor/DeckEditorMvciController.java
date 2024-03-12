package com.kyloapps.deckeditor;

import com.kyloapps.domain.Deck;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;

public class DeckEditorMvciController {
    private final DeckEditorMvciViewBuilder viewBuilder;

    public DeckEditorMvciController(ObservableList<Deck> deckListProperty) {
        DeckEditorMvciModel model = new DeckEditorMvciModel();
        DeckEditorMvciInteractor interactor = new DeckEditorMvciInteractor(model);
        viewBuilder = new DeckEditorMvciViewBuilder(
                model,
                interactor::createDeck,
                interactor::deleteDeck,
                interactor::confirmEditDeck,
                interactor::createCardEditor,
                interactor::saveChanges,
                interactor::revertChanges
        );
        Bindings.bindContentBidirectional(model.getDecks(), deckListProperty);
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
