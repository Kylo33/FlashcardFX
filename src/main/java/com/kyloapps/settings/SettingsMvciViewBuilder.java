package com.kyloapps.settings;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import javafx.util.StringConverter;

public class SettingsMvciViewBuilder implements Builder<Region> {
    private final SettingsMvciModel model;

    public SettingsMvciViewBuilder(SettingsMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        VBox result = new VBox(createAppearanceTile());
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