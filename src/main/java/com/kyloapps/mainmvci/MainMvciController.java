package com.kyloapps.mainmvci;

import com.kyloapps.domain.Deck;
import com.kyloapps.home.HomeMvciController;
import com.kyloapps.home.HomeMvciInteractor;
import com.kyloapps.practice.PracticeMvciController;
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
    private final PracticeMvciController practiceMvciController;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        homeMvciController = new HomeMvciController(model.getDecks(),
                model.selectedPageProperty(),
                model.currentDeckProperty());
        settingsMvciController = new SettingsMvciController(model.currentDirectoryProperty());
        practiceMvciController = new PracticeMvciController(model.currentDeckProperty());
        view = new MainMvciViewBuilder(
                model,
                homeMvciController.getView(),
                settingsMvciController.getView(),
                practiceMvciController.getView()
        );
    }
    public Region getView() {
        return view.build();
    }
}
