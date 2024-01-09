package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Theme;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Settings extends VBox {

    private final ObjectProperty<Theme> currentTheme;

    public Settings(Theme[] themes, ObjectProperty<Theme> currentTheme, ObjectProperty<File> flashcardDirectory) {
        // Store the current theme property
        this.currentTheme = currentTheme;

        // Set up the layout of the settings panel.
        setSpacing(15);
        setPadding(new Insets(30));

        // Add the tiles and separators
        getChildren().add(buildDirectoryNode(flashcardDirectory));
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(buildAppearanceNode(themes));
    }

    private Node buildDirectoryNode(ObjectProperty<File> flashcardDirectory) {
        // Create the base tile with an icon.
        Tile tile = new Tile("Flashcard Directory",
                "Choose a directory to store your decks of flashcards.",
                new FontIcon(MaterialDesignF.FILE));

        // If they have already selected a directory, apply it now.
        if (flashcardDirectory.get() != null) {
            tile.setDescription(flashcardDirectory.get().getAbsolutePath());
        }

        // Bind changes in the flashcardDirectory to the tile's description.
        flashcardDirectory.addListener((observable, oldValue, newValue) -> {
            tile.setDescription(newValue.getAbsolutePath());
        });

        // Add a button to the tile
        Button button = new Button("Files");
        tile.setAction(button);

        // Style the button
        button.setDefaultButton(true);

        // Add the button's function
        button.setOnAction((event) -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File choice = chooser.showDialog(getScene().getWindow());
            if (choice != null) {
                flashcardDirectory.setValue(choice);
            }
        });

        return tile;
    }

    private Node buildAppearanceNode(Theme[] themes) {
        // Create the base tile with an icon.
        Tile tile = new Tile("Customize Appearance",
                "Choose a color scheme to match your tastes.",
                new FontIcon(MaterialDesignW.WHITE_BALANCE_SUNNY));

        // Create a drop-down list and add it to the tile.
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        tile.setAction(choiceBox);

        // Get names for all the possible themes added to the list.
        List<String> themeNames = Arrays.stream(themes).map(Theme::getName).collect(Collectors.toList());
        choiceBox.getItems().addAll(themeNames);
        choiceBox.setValue(currentTheme.get().getName());

        // Bind the choiceBox to the model's currentTheme property.
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentTheme.setValue(themes[(int) newValue]);
        });

        return tile;
    }
}
