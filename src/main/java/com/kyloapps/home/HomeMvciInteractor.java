package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import com.kyloapps.mainmvci.Page;
import javafx.beans.property.ObjectProperty;

public class HomeMvciInteractor {
    private final ObjectProperty<Page> selectedPageProperty;
    private final ObjectProperty<Deck> currentDeckProperty;
    private final HomeMvciModel model;
    public HomeMvciInteractor(HomeMvciModel model,
                              ObjectProperty<Page> selectedPageProperty,
                              ObjectProperty<Deck> currentDeckProperty) {
        this.model = model;
        this.selectedPageProperty = selectedPageProperty;
        this.currentDeckProperty = currentDeckProperty;
    }
    public void beginPractice(Deck deck) {
        selectedPageProperty.setValue(Page.PRACTICE);
        currentDeckProperty.setValue(deck);
    }
}
