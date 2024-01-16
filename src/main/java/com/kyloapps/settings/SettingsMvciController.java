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

        // When this is bound, SettingsMvciModel model has a weak reference to currentDirectoryProperty
        currentDirectoryProperty.bind(model.currentDirectoryProperty());

        // TODO: clean this up, I have not figured out how to resolve the property being garbage collected.
        model.currentDirectoryProperty().addListener((observable, oldValue, newValue) -> {
            currentDirectoryProperty.get();
        });
    }

    public Region getView() {
        return view.build();
    }
}
