package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import com.kyloapps.Model.Model;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.io.File;
import java.util.EnumMap;
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
        setTop(new NavigationBar(presentationModel.currentPageProperty(), pageSelectHandler, pages));

        // Set the starting page.
        pageSelectHandler.accept(pages.get(Pages.MENU));
    }

    public Map<Pages, Node> getPages() {
        return pages;
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
