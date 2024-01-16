package com.kyloapps.mainmvci;

import com.kyloapps.domain.Deck;
import com.kyloapps.home.HomeMvciController;
import com.kyloapps.settings.SettingsMvciController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Region;

import java.io.File;

public class MainMvciController {
    private final MainMvciViewBuilder view;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        view = new MainMvciViewBuilder(
                model,
                new SettingsMvciController(model.currentDirectoryProperty()).getView(),
                new HomeMvciController(model.getDecks()).getView()
        );
    }
    public Region getView() {
        return view.build();
    }
}
