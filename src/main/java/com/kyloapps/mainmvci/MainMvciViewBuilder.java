package com.kyloapps.mainmvci;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private final Region editorContent;

    public MainMvciViewBuilder(MainMvciModel model, Region homeContent, Region settingsContent, Region practiceContent, Region editorContent) {
        this.model = model;
        this.settingsContent = settingsContent;
        this.homeContent = homeContent;
        this.practiceContent = practiceContent;
        this.editorContent = editorContent;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        result.setTop(createToolbar());
        result.setCenter(createStackPane());
        return result;
    }

    private Node createStackPane() {
        Region[] pages = {homeContent, settingsContent, practiceContent, editorContent};
        for (Region page: pages) {
            page.setVisible(false);
        }
        StackPane result = new StackPane(pages);

        Map<Page, Region> pageRegionMap = new HashMap<>();
        pageRegionMap.put(Page.HOME, homeContent);
        pageRegionMap.put(Page.SETTINGS, settingsContent);
        pageRegionMap.put(Page.PRACTICE, practiceContent);
        pageRegionMap.put(Page.EDITOR, editorContent);

        // Make the new page visible and the old page invisible
        model.selectedPageProperty().addListener((observable, oldPage, newPage) -> {
            Region newRegion = pageRegionMap.get(newPage);
            Region oldRegion = pageRegionMap.get(oldPage);
            if (newRegion != null) newRegion.setVisible(true);
            if (oldRegion != null) oldRegion.setVisible(false);
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


        Map<Page, Toggle> pageToggleMap = new HashMap<>();
        Map<Toggle, Page> togglePageMap = new HashMap<>();
        pageToggleMap.put(Page.HOME, homeButton);
        togglePageMap.put(homeButton, Page.HOME);
        pageToggleMap.put(Page.EDITOR, editorButton);
        togglePageMap.put(editorButton, Page.EDITOR);
        pageToggleMap.put(Page.SETTINGS, settingsButton);
        togglePageMap.put(settingsButton, Page.SETTINGS);
        model.selectedPageProperty().addListener((observable, oldPage, newPage) -> {
            if (pageToggleMap.containsKey(newPage)) {
                pageToggleMap.get(newPage).setSelected(true);
            } else toolbarButtonGroup.getSelectedToggle().setSelected(false);
        });

        toolbarButtonGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == null) return;
            Page newPage = togglePageMap.get(newToggle);
            model.setSelectedPage(newPage);
        });

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().addAll(homeButton, spacer, editorButton, settingsButton);
        return toolBar;
    }
}
