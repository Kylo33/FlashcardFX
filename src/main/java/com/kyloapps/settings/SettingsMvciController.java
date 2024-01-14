package com.kyloapps.settings;

import javafx.scene.layout.Region;

public class SettingsMvciController {
    private final SettingsMvciViewBuilder view;
    public SettingsMvciController() {
        SettingsMvciModel model = new SettingsMvciModel();
        SettingsMvciInteractor interactor = new SettingsMvciInteractor();
        view = new SettingsMvciViewBuilder(model);
    }

    public Region getView() {
        return view.build();
    }
}
