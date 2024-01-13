package com.kyloapps.View;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NavigationBar extends ToolBar {
    private RadioButton menuBtn;
    private RadioButton editorBtn;
    private RadioButton settingsBtn;
    private ToggleGroup group;

    public NavigationBar(ObjectProperty<Node> currentPageProperty, Consumer<Node> pageSelectHandler, Map<Pages, Node> pages) {
        initButtons(pageSelectHandler, pages);
        groupButtons();
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getItems().addAll(menuBtn, spacer, editorBtn, settingsBtn);
        listenForPageChanges(currentPageProperty, pages);
    }

    private void listenForPageChanges(ObjectProperty<Node> currentPageProperty, Map<Pages, Node> pages) {
        currentPageProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(pages.get(Pages.MENU))) {
                menuBtn.setSelected(true);
            } else if (newValue.equals(pages.get(Pages.EDITOR))) {
                editorBtn.setSelected(true);
            } else if (newValue.equals(pages.get(Pages.SETTINGS))) {
                settingsBtn.setSelected(true);
            } else {
                group.getSelectedToggle().setSelected(false);
            }
        });
    }

    private void initButtons(Consumer<Node> pageSelectHandler, Map<Pages, Node> pages) {
        // Create the buttons, assign their icons, and add their respective events.
        menuBtn = new RadioButton("Menu");
        menuBtn.setGraphic(new FontIcon(MaterialDesignH.HOME));
        menuBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.MENU));
        });

        editorBtn = new RadioButton("Deck Editor");
        editorBtn.setGraphic(new FontIcon(MaterialDesignP.PENCIL));
        editorBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.EDITOR));
        });

        settingsBtn = new RadioButton("Settings");
        settingsBtn.setGraphic(new FontIcon(MaterialDesignC.COG));
        settingsBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.SETTINGS));
        });
    }

    private void groupButtons() {
        // Add all the buttons to a ToggleGroup
        group = new ToggleGroup();
        List.of(menuBtn, editorBtn, settingsBtn).forEach((element) -> {
            element.setToggleGroup(group);

            // Style them as ToggleButton
            element.getStyleClass().remove("radio-button");
            element.getStyleClass().add("toggle-button");
        });
    }
}
