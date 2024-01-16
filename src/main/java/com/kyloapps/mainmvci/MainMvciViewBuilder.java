package com.kyloapps.mainmvci;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
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
        Region[] pages = {homeContent, settingsContent, practiceContent};
        for (Region page: pages) {
            page.setVisible(false);
        }
        StackPane result = new StackPane(pages);

        Map<Page, Region> pageRegionMap = new HashMap<>();
        pageRegionMap.put(Page.HOME, homeContent);
        pageRegionMap.put(Page.SETTINGS, settingsContent);
        pageRegionMap.put(Page.PRACTICE, practiceContent);

        model.selectedPageProperty().addListener((observable, oldPage, newPage) -> {
            pageRegionMap.get(newPage).setVisible(true);
            if (oldPage != null) pageRegionMap.get(oldPage).setVisible(false);
        });
        model.setSelectedPage(Page.HOME);

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

        BiMap<Page, RadioButton> pageButtonMap = HashBiMap.create();
        pageButtonMap.put(Page.HOME, homeButton);
        pageButtonMap.put(Page.EDITOR, editorButton);
        pageButtonMap.put(Page.SETTINGS, settingsButton);
        model.selectedPageProperty().addListener((observable, oldPage, newPage) -> {
            if (pageButtonMap.containsKey(newPage)) {
                pageButtonMap.get(newPage).setSelected(true);
            }
        });

        toolbarButtonGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            Page newPage = pageButtonMap.inverse().get(newToggle);
            model.setSelectedPage(newPage);
        });

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().addAll(homeButton, spacer, editorButton, settingsButton);
        return toolBar;
    }
}
