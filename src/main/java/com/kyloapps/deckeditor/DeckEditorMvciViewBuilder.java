package com.kyloapps.deckeditor;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Builder;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class DeckEditorMvciViewBuilder implements Builder<Region> {
    private static final int MAX_DECK_NAME_LENGTH = 10;
    private final DeckEditorMvciModel model;
    private final ModalPane modalPane = new ModalPane();
    private final Runnable deleteDeckAction;
    private final Runnable createDeckAction;
    private final Runnable editDeckAction;
    private final ObservableList<CardEditorMvciController> cardEditorControllers = FXCollections.observableArrayList();
    private final ObservableList<Node> mappedCardEditors = EasyBind.mapBacked(
            cardEditorControllers, cardEditor -> new VBox(cardEditor.getView(), new Separator(Orientation.HORIZONTAL)));

    public DeckEditorMvciViewBuilder(DeckEditorMvciModel model, Runnable createDeckAction, Runnable deleteDeckAction, Runnable editDeckAction) {
        this.model = model;
        this.createDeckAction = createDeckAction;
        this.deleteDeckAction = deleteDeckAction;
        this.editDeckAction = editDeckAction;
    }

    @Override
    public Region build() {
        StackPane result = new StackPane();
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(30));
        content.setTop(new VBox(createGeneralDeckEditor(), new Separator(Orientation.HORIZONTAL)));
        content.setCenter(createCardEditor());
        result.getChildren().addAll(modalPane, content);
        return result;
    }

    private Node createCardEditor() {
        ScrollPane result = new ScrollPane();
        result.setFitToWidth(true);
        VBox cardEditorBox = new VBox();
        Bindings.bindContent(cardEditorBox.getChildren(), mappedCardEditors);
        result.setContent(new VBox(cardEditorBox, createNewCardRegion(() -> {
            cardEditorControllers.add(new CardEditorMvciController());
        })));
        return result;
    }

    private Region createNewCardRegion(Runnable action) {
        Button newCardButton = new Button("New Card", new FontIcon(MaterialDesignP.PLUS));
        newCardButton.setOnAction((event) -> action.run());
        HBox buttonWrapper = new HBox(newCardButton);
        buttonWrapper.setAlignment(Pos.CENTER);
        newCardButton.getStyleClass().add(Styles.SUCCESS);
        return buttonWrapper;
    }

    private Node createGeneralDeckEditor() {
        GridPane result = new GridPane();
        result.add(createDeckSwitcher(), 0, 0);
        result.add(createDeckDetailer(), 0, 1);
        result.add(createDeckCreator(), 1, 0);
        result.add(createDeckDeleter(), 1, 1);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50d);
        result.getColumnConstraints().add(columnConstraints);

        result.getChildren().forEach(child -> GridPane.setHgrow(child, Priority.ALWAYS));

        return result;
    }

    private Node createDeckDeleter() {
        Tile result = new Tile("Delete Deck", "Delete the selected deck.");
        Button deleteButton = new Button("Delete", new FontIcon(MaterialDesignT.TRASH_CAN));
        deleteButton.getStyleClass().add(Styles.DANGER);
        deleteButton.setOnAction(event -> {
            modalPane.show(createDeckDeletionDialog());
        });
        deleteButton.disableProperty().bind(Bindings.createBooleanBinding(() -> model.getCurrentDeck() == null, model.currentDeckProperty()));
        result.setAction(deleteButton);
        return result;
    }

    private Region createDeckDeletionDialog() {
        Text deckNameText = new Text();
        deckNameText.textProperty().bind(model.editingDeckNameProperty());
        deckNameText.setStyle("-fx-fill: -color-accent-emphasis;");
        TextFlow confirmation = new TextFlow(new Text("Confirm deletion of "), deckNameText);

        confirmation.getStyleClass().add(Styles.TITLE_4);

        Label deletionInfo = new Label("Deck and its contents will be permanently deleted. Be careful.");
        deletionInfo.getStyleClass().add(Styles.TEXT_MUTED);
        Button deleteButton = new Button("Delete", new FontIcon(MaterialDesignT.TRASH_CAN));
        deleteButton.getStyleClass().add(Styles.DANGER);
        deleteButton.setOnAction((event) -> {
            deleteDeckAction.run();
            modalPane.hide();
        });

        HBox buttonBox = new HBox(deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(15, confirmation, deletionInfo, buttonBox);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);

        DeckEditorDialog dialog = new DeckEditorDialog(modalPane, hBox, null);
        return dialog.build();
    }

    private Node createDeckCreator() {
        Tile result = new Tile("Create Deck", "Create a new deck.");
        Button createButtonTileAction = new Button("Create", new FontIcon(MaterialDesignP.PLUS));
        createButtonTileAction.getStyleClass().add(Styles.SUCCESS);
        result.setAction(createButtonTileAction);

        createButtonTileAction.setOnAction(event -> {
            modalPane.show(createDeckCreationDialog());
        });

        return result;
    }

    private Region createDeckCreationDialog() {
        Tile deckTitleTile = new Tile("Deck Title", "Give your deck a descriptive name!");
        TextField titleField = new TextField();
        titleField.textProperty().bindBidirectional(model.newDeckNameProperty());
        deckTitleTile.setAction(titleField);

        Tile deckDescriptionTile = new Tile("Deck Description", "Give your deck a description.");
        TextField descriptionField = new TextField();
        descriptionField.textProperty().bindBidirectional(model.newDeckDescriptionProperty());
        deckDescriptionTile.setAction(descriptionField);

        VBox content = new VBox(15, deckTitleTile, new Separator(Orientation.HORIZONTAL), deckDescriptionTile);
        content.setPadding(new Insets(15));

        Button createButton = new Button("Create Deck", new FontIcon(MaterialDesignP.PLUS));
        createButton.getStyleClass().add(Styles.SUCCESS);
        BorderPane.setMargin(createButton, new Insets(15));
        BorderPane.setAlignment(createButton, Pos.BOTTOM_RIGHT);
        createButton.setOnAction((event) -> {
            createDeckAction.run();
            modalPane.hide();
        });

        DeckEditorDialog dialog = new DeckEditorDialog(modalPane, content, createButton);
        return dialog.build();
    }

    private Node createDeckDetailer() {
        Tile result = new Tile("Edit Deck Details", "Edit the details of the current deck.");
        Button editButton = new Button("Edit", new FontIcon(MaterialDesignF.FORM_TEXTBOX));
        result.setAction(editButton);
        editButton.setOnAction((event) -> {
            modalPane.show(createDeckDetailDialog());
        });
        editButton.disableProperty().bind(Bindings.createBooleanBinding(() -> model.getCurrentDeck() == null, model.currentDeckProperty()));
        return result;
    }

    private Region createDeckDetailDialog() {
        Tile deckTitleTile = new Tile("Deck Title", "Give your deck a descriptive name!");
        TextField titleField = new TextField(model.getCurrentDeck().getTitle());
        model.editingDeckNameProperty().unbind();
        model.editingDeckNameProperty().bind(titleField.textProperty());
        deckTitleTile.setAction(titleField);

        Tile deckDescriptionTile = new Tile("Deck Description", "Give your deck a description.");
        TextField descriptionField = new TextField(model.getCurrentDeck().getDescription());
        model.editingDeckDescriptionProperty().unbind();
        model.editingDeckDescriptionProperty().bind(descriptionField.textProperty());
        deckDescriptionTile.setAction(descriptionField);

        VBox content = new VBox(15, deckTitleTile, new Separator(Orientation.HORIZONTAL), deckDescriptionTile);
        content.setPadding(new Insets(15));

        Button saveChanges = new Button("Save Changes", new FontIcon(MaterialDesignF.FLOPPY));
        saveChanges.getStyleClass().add(Styles.SUCCESS);
        BorderPane.setMargin(saveChanges, new Insets(15));
        BorderPane.setAlignment(saveChanges, Pos.BOTTOM_RIGHT);
        saveChanges.setOnAction((event) -> {
            editDeckAction.run();
            modalPane.hide();
        });

        DeckEditorDialog dialog = new DeckEditorDialog(modalPane, content, saveChanges);
        return dialog.build();
    }

    private Node createDeckSwitcher() {
        Tile result = new Tile("Switch Decks", "Choose another deck to edit.");

        ComboBox<Deck> deckComboBox = new ComboBox<>();
        deckComboBox.setItems(model.getDecks());
        deckComboBox.valueProperty().bindBidirectional(model.currentDeckProperty());
        deckComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Deck deck) {
                if (deck == null)
                    return "";

                String title;
                if (deck.getTitle().length() > MAX_DECK_NAME_LENGTH)
                    title = deck.getTitle().substring(0, MAX_DECK_NAME_LENGTH - 1) + "...";
                else
                    title = deck.getTitle();
                return title + " (" + deck.getFlashcards().size() + " cards)";
            }

            @Override
            public Deck fromString(String s) {
                return null;
            }
        });

        result.setAction(deckComboBox);
        return result;
    }
}
