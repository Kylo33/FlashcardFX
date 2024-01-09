package com.kyloapps.Model;

import atlantafx.base.theme.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.prefs.Preferences;

import java.io.File;

public class Model {
    private final Preferences preferences;
    private PresentationModel presentationModel;
    private final ObjectProperty<File> flashcardDirectory;
    private final DeckLoader deckLoader;
    private final PracticeDeckManager practiceDeckManager = new PracticeDeckManager();

    public Model() {
        preferences = Preferences.userRoot().node("flashcardApp");

        applyTheme();

        flashcardDirectory = new SimpleObjectProperty<>();
        applyDirectory();

        deckLoader = new DeckLoader(flashcardDirectoryProperty());
    }

    private void applyDirectory() {
        String dir = preferences.get("flashcardDirectory", null);
        if (dir != null) {
            flashcardDirectory.set(new File(dir));
        }
        flashcardDirectory.addListener((observable, oldValue, newValue) -> {
            preferences.put("flashcardDirectory", newValue.getAbsolutePath());
        });
    }

    private void applyTheme() {
        // Set the theme at the app's start.
        this.presentationModel = new PresentationModel(preferences.getInt("theme", 0));

        // When they choose a new theme, save that in the user's preferences.
        this.presentationModel.currentThemeProperty().addListener((observable, oldValue, newValue) -> {
            preferences.putInt("theme", Arrays.asList(presentationModel.getThemes()).indexOf(newValue));
        });
    }

    public File getFlashcardDirectory() {
        return flashcardDirectory.get();
    }

    public ObjectProperty<File> flashcardDirectoryProperty() {
        return flashcardDirectory;
    }

    public void setFlashcardDirectory(File flashcardDirectory) {
        this.flashcardDirectory.set(flashcardDirectory);
    }

    public PresentationModel getPresentationModel() {
        return presentationModel;
    }

    public DeckLoader getDeckLoader() {
        return deckLoader;
    }

    public PracticeDeckManager getPracticeDeckManager() {
        return practiceDeckManager;
    }

    public static class PresentationModel {
        private final Theme[] themes = new Theme[] {
                new PrimerLight(),
                new PrimerDark(),
                new CupertinoLight(),
                new CupertinoDark(),
                new NordLight(),
                new NordDark(),
                new Dracula()
        };
        private final ObjectProperty<Theme> currentTheme;
        private final ObjectProperty<Node> currentPage = new SimpleObjectProperty<>();

        public PresentationModel(int themeIndex) {
            currentTheme = new SimpleObjectProperty<>(themes[themeIndex]);
        }

        public Node getCurrentPage() {
            return currentPage.get();
        }

        public ObjectProperty<Node> currentPageProperty() {
            return currentPage;
        }

        public void setCurrentPage(Node currentPage) {
            this.currentPage.set(currentPage);
        }

        public Theme[] getThemes() {
            return themes;
        }

        public Theme getCurrentTheme() {
            return currentTheme.get();
        }

        public ObjectProperty<Theme> currentThemeProperty() {
            return currentTheme;
        }

        public void setCurrentTheme(Theme currentTheme) {
            this.currentTheme.set(currentTheme);
        }
    }
}
