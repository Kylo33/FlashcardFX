package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import com.kyloapps.Model.DeckLoader;
import com.kyloapps.Model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class View extends BorderPane {
    private final Model.PresentationModel presentationModel;
    private final Consumer<Node> pageSelectHandler;
    private final Map<Pages, Node> pages;

    public View(Model.PresentationModel presentationModel, Consumer<Node> pageSelectHandler,
                ObjectProperty<File> flashcardDirectory, BiConsumer<String, String> detailChangeHandler,
                Runnable saveHandler, Consumer<Pair<String, String>> createDeckActionHandler) {
        this.presentationModel = presentationModel;
        this.pageSelectHandler = pageSelectHandler;

        // Create the different pages
        pages = new EnumMap<>(Pages.class);
        pages.put(Pages.MENU, new Menu());
        pages.put(Pages.EDITOR, new Editor(detailChangeHandler, saveHandler, presentationModel.currentThemeProperty(),
                createDeckActionHandler));
        pages.put(Pages.SETTINGS, new Settings(this.presentationModel.getThemes(), this.presentationModel.currentThemeProperty(),
                flashcardDirectory));

        // Subscribe to currentPage events.
        this.presentationModel.currentPageProperty().addListener((observable, oldValue, newValue) -> {
            setCenter(newValue);
        });

        // Add the toolbar
        setTop(createToolbar());

        // Set the starting page.
        pageSelectHandler.accept(pages.get(Pages.MENU));
    }

    public Map<Pages, Node> getPages() {
        return pages;
    }

    private Node createToolbar() {
        ToolBar bar = new ToolBar();

        // Create the buttons, assign their icons, and add their respective events.
        RadioButton menuBtn = new RadioButton("Menu");
        menuBtn.setGraphic(new FontIcon(MaterialDesignH.HOME));
        menuBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.MENU));
        });

        RadioButton statsBtn = new RadioButton("Deck Editor");
        statsBtn.setGraphic(new FontIcon(MaterialDesignP.PENCIL));
        statsBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.EDITOR));
        });

        RadioButton settingsBtn = new RadioButton("Settings");
        settingsBtn.setGraphic(new FontIcon(MaterialDesignC.COG));
        settingsBtn.setOnAction((event) -> {
            pageSelectHandler.accept(pages.get(Pages.SETTINGS));
        });

        // Add all the buttons to a ToggleGroup
        ToggleGroup group = new ToggleGroup();
        List.of(menuBtn, statsBtn, settingsBtn).forEach((element) -> {
            element.setToggleGroup(group);

            // Style them as ToggleButton
            element.getStyleClass().remove("radio-button");
            element.getStyleClass().add("toggle-button");
        });

        // Spacer to right-align the stats and settings buttons.
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add items
        bar.getItems().addAll(menuBtn, spacer, statsBtn, settingsBtn);

        // Listen for page changes to update the toolbar (if page is not changed via toolbar)
        presentationModel.currentPageProperty().addListener(pageChangeListenerForBar(menuBtn, statsBtn, settingsBtn, group));

        return bar;
    }

    private ChangeListener<Node> pageChangeListenerForBar(RadioButton menuBtn, RadioButton statsBtn, RadioButton settingsBtn, ToggleGroup group) {
        return (observable, oldValue, newValue) -> {
            if (newValue.equals(pages.get(Pages.MENU))) {
                menuBtn.setSelected(true);
            } else if (newValue.equals(pages.get(Pages.EDITOR))) {
                statsBtn.setSelected(true);
            } else if (newValue.equals(pages.get(Pages.SETTINGS))) {
                settingsBtn.setSelected(true);
            } else {
                group.getSelectedToggle().setSelected(false);
            }
        };
    }

    public void loading(boolean loading) {
        if (loading) {
            Label label = new Label("Loading...");
            label.getStyleClass().addAll(Styles.TEXT_MUTED, Styles.LARGE);
            HBox loadingBox = new HBox(label);
            loadingBox.setStyle("-fx-background-color: -color-accent-subtle;");
            loadingBox.setPadding(new Insets(10));
            loadingBox.setAlignment(Pos.CENTER);
            setBottom(loadingBox);
        } else {
            setBottom(null);
        }
    }
}
