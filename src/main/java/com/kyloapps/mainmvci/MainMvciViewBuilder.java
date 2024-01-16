package com.kyloapps.mainmvci;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.List;

public class MainMvciViewBuilder implements Builder<Region> {
    private final MainMvciModel model;
    private final Region settingsContent;
    private final Region homeContent;
    public MainMvciViewBuilder(MainMvciModel model, Region settingsContent, Region homeContent) {
        this.model = model;
        this.settingsContent = settingsContent;
        this.homeContent = homeContent;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        result.setTop(createToolbar());
        result.setCenter(createStackPane());
        return result;
    }

    private Node createStackPane() {
        StackPane result = new StackPane(
                settingsContent,
                homeContent
        );
        settingsContent.visibleProperty().bind(model.settingsPageSelectedProperty());
        homeContent.visibleProperty().bind(model.homePageSelectedProperty());
        return result;
    }

    private Node createToolbar() {
        ToolBar toolBar = new ToolBar();
        RadioButton menuButton = new RadioButton("Menu");
        RadioButton editorButton = new RadioButton("Deck Editor");
        RadioButton settingsButton = new RadioButton("Settings");
        menuButton.setGraphic(new FontIcon(MaterialDesignH.HOME));
        editorButton.setGraphic(new FontIcon(MaterialDesignP.PENCIL));
        settingsButton.setGraphic(new FontIcon(MaterialDesignC.COG));
        menuButton.selectedProperty().bindBidirectional(model.homePageSelectedProperty());
        editorButton.selectedProperty().bindBidirectional(model.editorPageSelectedProperty());
        settingsButton.selectedProperty().bindBidirectional(model.settingsPageSelectedProperty());
        ToggleGroup toolbarButtonGroup = new ToggleGroup();
        List.of(menuButton, editorButton, settingsButton).forEach(button -> {
            button.setToggleGroup(toolbarButtonGroup);
            button.getStyleClass().remove("radio-button");
            button.getStyleClass().add("toggle-button");
        });
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().addAll(menuButton, spacer, editorButton, settingsButton);
        return toolBar;
    }
}
