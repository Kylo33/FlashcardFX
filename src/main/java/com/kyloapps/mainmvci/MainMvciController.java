package com.kyloapps.mainmvci;

import com.kyloapps.deckeditor.DeckEditorMvciController;
import com.kyloapps.home.HomeMvciController;
import com.kyloapps.practice.PracticeMvciController;
import com.kyloapps.settings.SettingsMvciController;
import javafx.scene.layout.Region;

public class MainMvciController {
    private final MainMvciViewBuilder view;
    private final HomeMvciController homeMvciController;
    private final SettingsMvciController settingsMvciController;
    private final PracticeMvciController practiceMvciController;
    private final DeckEditorMvciController deckEditorMvciController;

    public MainMvciController() {
        MainMvciModel model = new MainMvciModel();
        MainMvciInteractor interactor = new MainMvciInteractor(model);
        homeMvciController = new HomeMvciController(model.getDecks(),
                model.selectedPageProperty(),
                model.currentDeckProperty());
        settingsMvciController = new SettingsMvciController(model.currentDirectoryProperty());
        practiceMvciController = new PracticeMvciController(model.currentDeckProperty());
        deckEditorMvciController = new DeckEditorMvciController(model.getDecks(), model.getDeckFileMap(), model.currentDirectoryProperty());
        view = new MainMvciViewBuilder(
                model,
                homeMvciController.getView(),
                settingsMvciController.getView(),
                practiceMvciController.getView(),
                deckEditorMvciController.getView()
        );
    }

    public Region getView() {
        return view.build();
    }
}
