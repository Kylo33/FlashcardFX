package com.kyloapps.settings;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Builder;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

import java.io.File;

public class SettingsMvciViewBuilder implements Builder<Region> {
    private final SettingsMvciModel model;

    public SettingsMvciViewBuilder(SettingsMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        VBox result = new VBox(15,
                createAppearanceTile(),
                new Separator(Orientation.HORIZONTAL),
                createDirectoryTile());
        result.setPadding(new Insets(30));
        return result;
    }

    private Node createDirectoryTile() {
        Tile result = new Tile("Flashcard directory", null);
        Button chooseDirectoryButton = new Button("Files", new FontIcon(MaterialDesignF.FILE));
        result.setAction(chooseDirectoryButton);
        chooseDirectoryButton.setOnAction((event) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose a Flashcard Directory");
            File choice = directoryChooser.showDialog(chooseDirectoryButton.getScene().getWindow());
            if (choice != null) {
                model.setCurrentDirectory(choice);
            }
        });

        // For some reason, an invisible border prevents the tile from moving/resizing itself when
        // the directory is changed.
        result.setBorder(
                new Border(new BorderStroke(null, null, null, null))
        );

        result.descriptionProperty().bind(Bindings.createStringBinding(() -> {
            File file = model.getCurrentDirectory();
            if (file != null) {
                return file.getAbsolutePath();
            } else {
                return "[Empty]";
            }
        }, model.currentDirectoryProperty()));

        return result;
    }

    private Node createAppearanceTile() {
        Tile result = new Tile("Program appearance", "Configure the appearance of the app.");
        ChoiceBox<Theme> themeChooser = new ChoiceBox<>();
        result.setAction(themeChooser);
        Theme[] themes = {
                new PrimerLight(),
                new PrimerDark(),
                new CupertinoLight(),
                new CupertinoDark(),
                new NordLight(),
                new NordDark(),
                new Dracula(),
        };
        themeChooser.getItems().setAll(themes);
        themeChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            Application.setUserAgentStylesheet(newValue.getUserAgentStylesheet());
        });
        themeChooser.setValue(themes[0]);
        themeChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Theme theme) {
                return theme.getName();
            }

            @Override
            public Theme fromString(String s) {
                return null;
            }
        });
        return result;
    }
}