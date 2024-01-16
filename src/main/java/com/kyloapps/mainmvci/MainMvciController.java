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
    private final ObservableList<Label> labels;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        view = new MainMvciViewBuilder(
                model,
                new SettingsMvciController(model.currentDirectoryProperty()).getView(),
                new HomeMvciController(model.getDecks()).getView()
        );

        labels = EasyBind.map(model.getDecks(), integer -> new Label(integer.getTitle()));
        labels.addListener((ListChangeListener<? super Label>) (a) -> {
            System.out.println("a.getList() = " + a.getList());
        });
    }
    public Region getView() {
        return view.build();
    }
}
