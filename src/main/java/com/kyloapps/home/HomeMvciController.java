package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;


public class HomeMvciController {
    private final HomeMvciViewBuilder view;
    public HomeMvciController(ObservableList<Deck> decks) {
        HomeMvciModel model = new HomeMvciModel();
        HomeMvciInteractor interactor = new HomeMvciInteractor(model);
        view = new HomeMvciViewBuilder(model);
    }

    public Region getView() {
        return view.build();
    }
}
