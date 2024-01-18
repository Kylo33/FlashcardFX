package com.kyloapps.mainmvci;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMvciViewBuilder implements Builder<Region> {
    private final MainMvciModel model;
    private final Region settingsContent;
    private final Region homeContent;
    private final Region practiceContent;
    public MainMvciViewBuilder(MainMvciModel model, Region homeContent, Region settingsContent, Region practiceContent) {
        this.model = model;
        this.settingsContent = settingsContent;
        this.homeContent = homeContent;
        this.practiceContent = practiceContent;
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
                homeContent,
                settingsContent,
                practiceContent
        );
        Map<Pages, Region> pagesRegionMap = new HashMap<>();
        pagesRegionMap.put(Pages.HOME, homeContent);
        pagesRegionMap.put(Pages.SETTINGS, settingsContent);
        pagesRegionMap.put(Pages.PRACTICE, practiceContent);
        model.pageSelectedProperty().addListener((observable, newValue, oldValue) -> {
            pagesRegionMap.get(newValue).setVisible(true);
            pagesRegionMap.get(oldValue).setVisible(false);
        });
        return result;
    }

    private Node createToolbar() {
        ToolBar toolBar = new ToolBar();
        RadioButton homeButton = new RadioButton("Home");
        RadioButton editorButton = new RadioButton("Deck Editor");
        RadioButton settingsButton = new RadioButton("Settings");
        homeButton.setGraphic(new FontIcon(MaterialDesignH.HOME));
        editorButton.setGraphic(new FontIcon(MaterialDesignP.PENCIL));
        settingsButton.setGraphic(new FontIcon(MaterialDesignC.COG));
        ToggleGroup toolbarButtonGroup = new ToggleGroup();
        List.of(homeButton, editorButton, settingsButton).forEach(button -> {
            button.setToggleGroup(toolbarButtonGroup);
            button.getStyleClass().remove("radio-button");
            button.getStyleClass().add("toggle-button");
        });

        registerPageSelectedListeners(toolbarButtonGroup, homeButton, editorButton, settingsButton);
        model.setPageSelected(Pages.HOME);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().addAll(homeButton, spacer, editorButton, settingsButton);
        return toolBar;
    }

    private void registerPageSelectedListeners(ToggleGroup toolbarButtonGroup, RadioButton homeButton, RadioButton editorButton, RadioButton settingsButton) {
        BooleanProperty changeInProgress = new SimpleBooleanProperty(false);
        toolbarButtonGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (changeInProgress.get()) {
                return;
            }
            changeInProgress.set(true);
            if (newValue.equals(homeButton)) {
                model.setPageSelected(Pages.HOME);
            } else if (newValue.equals(editorButton)) {
                model.setPageSelected(Pages.EDITOR);
            } else if (newValue.equals(settingsButton)) {
                model.setPageSelected(Pages.SETTINGS);
            }
            changeInProgress.set(false);
        });

        model.pageSelectedProperty().addListener((observable, oldValue, newValue) -> {
            if (changeInProgress.get()) {
                return;
            }
            changeInProgress.set(true);
            switch (newValue) {
                case HOME:
                    homeButton.setSelected(true);
                    break;
                case EDITOR:
                    editorButton.setSelected(true);
                    break;
                case SETTINGS:
                    settingsButton.setSelected(true);
                    break;
                default:
                    toolbarButtonGroup.getSelectedToggle().setSelected(false);
                    break;
            }
            changeInProgress.set(false);
        });
    }
}
