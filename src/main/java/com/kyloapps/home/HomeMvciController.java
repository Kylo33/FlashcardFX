package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import com.kyloapps.mainmvci.Page;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;


public class HomeMvciController {
    private final HomeMvciViewBuilder view;
    public HomeMvciController(ObservableList<Deck> decks,
                              ObjectProperty<Page> selectedPageProperty,
                              ObjectProperty<Deck> currentDeckProperty) {
        HomeMvciModel model = new HomeMvciModel();
        HomeMvciInteractor interactor = new HomeMvciInteractor(model, selectedPageProperty, currentDeckProperty);
        view = new HomeMvciViewBuilder(model, interactor::beginPractice);
        Bindings.bindContent(model.getDecks(), decks);
    }

    public Region getView() {
        return view.build();
    }
}
