package com.kyloapps.settings;

import java.io.File;
import java.util.prefs.Preferences;

public class SettingsMvciInteractor {
    private final SettingsMvciModel model;
    private final Preferences preferences = Preferences.userRoot().node(this.getClass().getPackageName());
    private static final String DECK_DIRECTORY_FIELD = "deckDirectory";
    private static final String THEME_FIELD = "currentTheme";

    public SettingsMvciInteractor(SettingsMvciModel model) {
        this.model = model;
        loadPreferences();
        listenToChangePreferences();
        System.out.println(preferences.absolutePath());
    }

    private void loadPreferences() {
        loadDirectoryFromPreferences();
        loadThemeFromPreferences();
    }

    private void loadThemeFromPreferences() {
        int themeIndex = preferences.getInt(THEME_FIELD, 0);
        model.setCurrentTheme(model.getThemes()[themeIndex]);
    }

    private void loadDirectoryFromPreferences() {
        String directoryPath = preferences.get(DECK_DIRECTORY_FIELD, null);
        if (directoryPath != null)
            this.model.setCurrentDirectory(new File(directoryPath));
    }

    private void listenToChangePreferences() {
        model.currentThemeProperty().addListener((observable, oldTheme, newTheme) -> {
            for (int themeIndex = 0, c = model.getThemes().length; themeIndex < c; themeIndex++) {
                if (model.getThemes()[themeIndex].equals(newTheme)) {
                    preferences.putInt(THEME_FIELD, themeIndex);
                    return;
                }
            }
        });
        model.currentDirectoryProperty().addListener(((observableValue, oldDirectory, newDirectory) -> {
            preferences.put(DECK_DIRECTORY_FIELD, newDirectory.getAbsolutePath());
        }));
    }

    public void chooseDirectory(File newDirectory) {
        this.model.setCurrentDirectory(newDirectory);

    }
}
