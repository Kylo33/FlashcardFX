package com.kyloapps.settings;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;

import java.io.File;

public class SettingsMvciController {
    private final SettingsMvciViewBuilder view;
    private final SettingsMvciInteractor interactor;
    private final SettingsMvciModel model;

    public SettingsMvciController(ObjectProperty<File> currentDirectoryProperty) {
        this.model = new SettingsMvciModel();
        this.interactor = new SettingsMvciInteractor(model);
        view = new SettingsMvciViewBuilder(model);
        currentDirectoryProperty.bind(model.currentDirectoryProperty());
    }

    public Region getView() {
        return view.build();
    }
}
