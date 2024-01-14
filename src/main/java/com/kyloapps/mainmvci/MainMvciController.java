package com.kyloapps.mainmvci;

import com.kyloapps.settings.SettingsMvciController;
import javafx.scene.layout.Region;

public class MainMvciController {
    private MainMvciViewBuilder view;
    
    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor();
        view = new MainMvciViewBuilder(model, new SettingsMvciController().getView());
        model.settingsPageSelectedProperty().addListener((obs, old, nww) -> {
            System.out.println(nww);
        });
    }
    public Region getView() {
        return view.build();
    }
}
