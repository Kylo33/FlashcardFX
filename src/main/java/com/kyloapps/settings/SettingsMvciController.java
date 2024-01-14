package com.kyloapps.settings;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;

import java.io.File;

public class SettingsMvciController {
    private final SettingsMvciViewBuilder view;
    public SettingsMvciController(ObjectProperty<File> currentDirectoryProperty) {
        SettingsMvciModel model = new SettingsMvciModel();
        SettingsMvciInteractor interactor = new SettingsMvciInteractor();
        view = new SettingsMvciViewBuilder(model);
        currentDirectoryProperty.bind(model.currentDirectoryProperty());
    }

    public Region getView() {
        return view.build();
    }
}
