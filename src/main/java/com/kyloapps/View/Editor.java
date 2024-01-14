package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Theme;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.Deck;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Editor extends BorderPane {
    private SimpleObjectProperty<ChoiceBox<Deck>> deckChoiceBox = new SimpleObjectProperty<>(new ChoiceBox<>());
    private HBox loadingBox;
    private SimpleObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private CardCreator creator;
    private ObjectProperty<Theme> currentTheme;

    public Editor(BiConsumer<String, String> detailChangeHandler, Runnable saveAction, ObjectProperty<Theme> currentThemeProperty,
                  Consumer<Pair<String, String>> createDeckActionHandler) {
        currentTheme = currentThemeProperty;
        Tile deckChooserTile = new Tile("Choose a deck", "Select a deck to edit.");
        deckChooserTile.actionProperty().bind(deckChoiceBox);

        Tile deckCreatorTile = new Tile("Create a deck", "Want a new deck? Create one here.");
        Button createButton = new Button("Create", new FontIcon(MaterialDesignP.PLUS));

        setCreateAction(createButton, createDeckActionHandler);

        deckCreatorTile.setAction(createButton);

        GridPane deckGrid = new GridPane();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50d);
        deckGrid.getColumnConstraints().add(columnConstraints);
        deckGrid.add(deckChooserTile,0,0);
        deckGrid.add(deckCreatorTile, 1, 0);
        deckGrid.getChildren().forEach((child) -> {
            GridPane.setHgrow(child, Priority.ALWAYS);
        });

        currentDeck.bind(deckChoiceBox.get().valueProperty());
        deckChoiceBoxProperty().addListener((obs, oldValue, newValue) -> {
            currentDeck.unbind();
            currentDeck.bind(newValue.valueProperty());
        });

        Tile deckDetailEditorTile = new Tile("Edit deck details", "Change the deck's title or description.");
        Button editTitleButton = new Button("Title", new FontIcon(MaterialDesignF.FORMAT_TITLE));
        Button editDescriptionButton = new Button("Description", new FontIcon(MaterialDesignT.TEXT));
        HBox buttonBox = new HBox(editTitleButton, editDescriptionButton);
        buttonBox.setSpacing(5);

        buttonBox.getChildren().forEach((child) -> child.setDisable(true));
        deckChoiceBox.get().getSelectionModel().selectedIndexProperty().addListener((obs, old, newValue) -> {
            buttonBox.getChildren().forEach((child) -> child.setDisable(false));
        });
        deckChoiceBox.addListener((obs, oldValue, newValue) -> {
            newValue.getSelectionModel().selectedIndexProperty().addListener((obs1, old1, newValue1) -> {
                buttonBox.getChildren().forEach((child) -> child.setDisable(false));
            });
        });

        editTitleButton.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog(currentDeck.get().getTitle());
            dialog.setTitle("Rename Deck");
            dialog.setHeaderText("Rename deck: " + currentDeck.get().getTitle());
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
            if (dialog.getResult() != null) {
                detailChangeHandler.accept(dialog.getResult(), null);
            }
        });

        editDescriptionButton.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog(currentDeck.get().getDescription());
            dialog.setTitle("Change Deck Description");
            dialog.setHeaderText("Change the description of deck: " + currentDeck.get().getTitle());
            dialog.initOwner(getScene().getWindow());
            dialog.showAndWait();
            if (dialog.getResult() != null) {
                detailChangeHandler.accept(null, dialog.getResult());
            }
        });

        deckDetailEditorTile.setAction(buttonBox);
        deckGrid.add(deckDetailEditorTile, 0, 1);

        Tile deckDeleterTile = new Tile("Delete deck", "Delete the current deck");
        Button deckDeleterButton = new Button("Delete", new FontIcon(MaterialDesignT.TRASH_CAN));
        deckDeleterButton.getStyleClass().add(Styles.DANGER);
        deckDeleterTile.setAction(deckDeleterButton);
        deckGrid.add(deckDeleterTile, 1, 1);

        VBox top = new VBox(deckGrid, new Separator(Orientation.HORIZONTAL));
        top.setPadding(new Insets(30));
        setTop(top);

        creator = new CardCreator();
        ScrollPane creatorPane = new ScrollPane(creator);
        BorderPane.setMargin(creatorPane, new Insets(0, 30, 0, 30));
        creatorPane.setFitToWidth(true);
        setCenter(creatorPane);

        creator.setVisible(false);
        deckChoiceBox.get().getSelectionModel().selectedIndexProperty().addListener((obs, old, newValue) -> {
            creator.setVisible(true);
        });

        addSaveBar(saveAction);
    }

    private void setCreateAction(Button createButton, Consumer<Pair<String, String>> createDeckActionHandler) {
        createButton.setOnAction((event) -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Create a deck");
            dialog.getDialogPane().getScene().setUserAgentStylesheet(currentTheme.get().getUserAgentStylesheet());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            GridPane dialogContent = new GridPane();
            dialogContent.setHgap(5);
            dialogContent.setVgap(5);
            dialogContent.setPadding(new Insets(30));
            dialogContent.add(new Label("Deck name:"), 0, 0);
            dialogContent.add(new Label("Deck description:"), 0, 1);
            TextField name = new TextField();
            dialogContent.add(name, 1, 0);
            TextField desc = new TextField();
            dialogContent.add(desc, 1, 1);

            dialog.getDialogPane().setContent(dialogContent);

            dialog.setResultConverter((dialogButton) -> {
                if (dialogButton == ButtonType.FINISH) {
                    return new Pair<>(name.getText(), desc.getText());
                }
                return null;
            });

            createDeckActionHandler.accept(dialog.showAndWait().get());
        });
    }

    private void addSaveBar(Runnable saveAction) {
        Label label = new Label("Save your changes...");
        label.getStyleClass().addAll(Styles.TEXT_MUTED, Styles.LARGE);
        Button saveBtn = new Button("Save", new FontIcon(MaterialDesignF.FLOPPY));
        saveBtn.getStyleClass().addAll(Styles.BUTTON_OUTLINED);
        saveBtn.setDefaultButton(true);
        loadingBox = new HBox(label, saveBtn);
        loadingBox.setSpacing(10);
        loadingBox.setStyle("-fx-background-color: -color-accent-subtle;");
        loadingBox.setPadding(new Insets(10));
        loadingBox.setAlignment(Pos.CENTER);
        setBottom(loadingBox);

        saveBtn.setOnAction((event) -> saveAction.run());
    }

    public ChoiceBox<Deck> getDeckChoiceBox() {
        return deckChoiceBox.get();
    }

    public SimpleObjectProperty<ChoiceBox<Deck>> deckChoiceBoxProperty() {
        return deckChoiceBox;
    }

    public CardCreator getCreator() {
        return creator;
    }

    public void setDeckChoiceBox(ChoiceBox<Deck> deckChoiceBox) {
        this.deckChoiceBox.set(deckChoiceBox);
    }
}
