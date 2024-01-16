package com.kyloapps.mainmvci;

import com.kyloapps.domain.Deck;
import com.kyloapps.home.HomeMvciController;
import com.kyloapps.settings.SettingsMvciController;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMvciController {
    private final MainMvciViewBuilder view;
    private final HomeMvciController homeMvciController;
    private final SettingsMvciController settingsMvciController;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        homeMvciController = new HomeMvciController(model.getDecks());
        settingsMvciController = new SettingsMvciController(model.currentDirectoryProperty());
        view = new MainMvciViewBuilder(
                model,
                settingsMvciController.getView(),
                homeMvciController.getView()
        );
    }
    public Region getView() {
        return view.build();
    }
}
