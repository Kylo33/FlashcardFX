package com.kyloapps.mainmvci;

import com.kyloapps.settings.SettingsMvciController;
import javafx.scene.layout.Region;

public class MainMvciController {
    private MainMvciViewBuilder view;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        view = new MainMvciViewBuilder(model, new SettingsMvciController(model.currentDirectoryProperty()).getView());
    }
    public Region getView() {
        return view.build();
    }
}
