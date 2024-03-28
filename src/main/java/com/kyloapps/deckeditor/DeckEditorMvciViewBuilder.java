package com.kyloapps.deckeditor;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import org.kordamp.ikonli.materialdesign2.*;

import java.util.function.Consumer;

public class DeckEditorMvciViewBuilder implements Builder<Region> {
    private static final int MAX_DECK_NAME_LENGTH = 10;
    private final DeckEditorMvciModel model;
    private final ModalPane modalPane = new ModalPane();
    private final Runnable deleteDeckAction;
    private final Runnable createDeckAction;
    private final Runnable editDeckAction;
    private final Runnable createCardEditorAction;
    private final Runnable saveAction;
    private final Runnable revertAction;
    private final Consumer<CardEditorMvciController> deleteCardAction;
    private final ObservableList<Node> mappedCardEditors;
    private final Runnable switchDecksAction;

    public DeckEditorMvciViewBuilder(DeckEditorMvciModel model,
                                     Runnable createDeckAction,
                                     Runnable deleteDeckAction,
                                     Runnable editDeckAction,
                                     Runnable createCardEditorAction,
                                     Runnable saveAction,
                                     Runnable revertAction,
                                     Consumer<CardEditorMvciController> deleteCardAction,
                                     Runnable switchDecksAction) {
        this.model = model;
        this.createDeckAction = createDeckAction;
        this.deleteDeckAction = deleteDeckAction;
        this.editDeckAction = editDeckAction;
        this.createCardEditorAction = createCardEditorAction;
        this.saveAction = saveAction;
        this.revertAction = revertAction;
        this.deleteCardAction = deleteCardAction;
        mappedCardEditors = EasyBind.mapBacked(model.getCardEditorControllers(), cardEditorController -> {
            VBox cardNodes = new VBox(15, cardEditorController.getView(), createDeleteCardRegion(cardEditorController));
            return new VBox(cardNodes, new Separator(Orientation.HORIZONTAL));
        });
        this.switchDecksAction = switchDecksAction;
    }

    private Node createDeleteCardRegion(CardEditorMvciController cardEditorController) {
        Tile deleteTile = new Tile("Delete Flashcard", "Delete this Flashcard?");
        Button deletionButton = new Button("Delete", new FontIcon(MaterialDesignT.TRASH_CAN));
        deletionButton.getStyleClass().add(Styles.DANGER);

        deletionButton.setOnAction((event) -> deleteCardAction.accept(cardEditorController));
        deleteTile.setAction(deletionButton);

        return deleteTile;
    }

    @Override
    public Region build() {
        StackPane result = new StackPane();
        BorderPane content = new BorderPane();

        VBox topBox = new VBox(createGeneralDeckEditor(), new Separator(Orientation.HORIZONTAL));
        BorderPane.setMargin(topBox, new Insets(30));
        content.setTop(topBox);

        Node cardEditor = createCardEditorRegion();
        BorderPane.setMargin(cardEditor, new Insets(0, 30, 30, 30));
        content.setCenter(cardEditor);

        content.setBottom(createSaveBar());
        result.getChildren().addAll(modalPane, content);
        return result;
    }

    private Node createSaveBar() {
        HBox result = new HBox(10);
        result.getStyleClass().add("save-bar");

        BooleanBinding changesWereMade = Bindings.createBooleanBinding(
                () -> model.getMasterDirtyProperty().isDirty(),
                model.getMasterDirtyProperty().isDirtyProperty());

        Button saveButton = new Button("Save Changes", new FontIcon(MaterialDesignF.FLOPPY));
        saveButton.setOnAction((event) -> saveAction.run());
        saveButton.getStyleClass().add(Styles.SUCCESS);
        saveButton.disableProperty().bind(changesWereMade.not());

        Button revertButton = new Button("Revert Changes", new FontIcon(MaterialDesignR.REFRESH));
        revertButton.setOnAction((event) -> revertAction.run());
        revertButton.getStyleClass().add(Styles.DANGER);
        revertButton.disableProperty().bind(changesWereMade.not());

        result.getChildren().addAll(saveButton, revertButton);
        result.setAlignment(Pos.CENTER);
        return result;
    }

    private Region createCardEditorRegion() {
        ScrollPane result = new ScrollPane();
        result.setFitToWidth(true);
        VBox cardEditorBox = new VBox();
        Bindings.bindContent(cardEditorBox.getChildren(), mappedCardEditors);
        result.setContent(new VBox(cardEditorBox, createNewCardRegion()));
        return result;
    }

    private Region createNewCardRegion() {
        Button newCardButton = new Button("New Card", new FontIcon(MaterialDesignP.PLUS));
        newCardButton.getStyleClass().add(Styles.SUCCESS);
        newCardButton.setOnAction((event) -> createCardEditorAction.run());
        newCardButton.disableProperty().bind(Bindings.createBooleanBinding(() -> model.getCurrentDeck() == null, model.currentDeckProperty()));

        HBox buttonWrapper = new HBox(newCardButton);
        buttonWrapper.setAlignment(Pos.CENTER);
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
        deckNameText.textProperty().bind(Bindings.createStringBinding(
                () -> model.getCurrentDeck() == null ? "" : model.getCurrentDeck().getTitle(), model.currentDeckProperty()));
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
        titleField.textProperty().bindBidirectional(model.creationDeckNameInputProperty());
        deckTitleTile.setAction(titleField);

        Tile deckDescriptionTile = new Tile("Deck Description", "Give your deck a description.");
        TextField descriptionField = new TextField();
        descriptionField.textProperty().bindBidirectional(model.creationDeckDescriptionInputProperty());
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
//        editButton.setOnAction((event) -> modalPane.show(createDeckDetailDialog()));
        editButton.disableProperty().bind(
                Bindings.createBooleanBinding(() -> model.getCurrentDeck() == null, model.currentDeckProperty())
        );
        return result;
    }

//    private Region createDeckDetailDialog() {
//        Tile deckTitleTile = new Tile("Deck Title", "Give your deck a descriptive name!");
//        TextField titleField = new TextField();
//        model.editingDeckNameProperty().unbind();
//        titleField.textProperty().bindBidirectional(model.editingDeckNameProperty());
//        deckTitleTile.setAction(titleField);
//
//        Tile deckDescriptionTile = new Tile("Deck Description", "Give your deck a description.");
//        TextField descriptionField = new TextField();
//        model.editingDeckDescriptionProperty().unbind();
//        descriptionField.textProperty().bindBidirectional(model.editingDeckDescriptionProperty());
//        deckDescriptionTile.setAction(descriptionField);
//
//        VBox content = new VBox(15, deckTitleTile, new Separator(Orientation.HORIZONTAL), deckDescriptionTile);
//        content.setPadding(new Insets(15));
//
//        DeckEditorDialog dialog = new DeckEditorDialog(modalPane, content, new Region());
//        return dialog.build();
//    }

    private Node createDeckSwitcher() {
        Tile result = new Tile("Switch Decks", "Choose another deck to edit.");

        ComboBox<Deck> deckComboBox = new ComboBox<>();
        deckComboBox.setItems(model.getDecks());
        deckComboBox.valueProperty().bindBidirectional(model.currentDeckProperty());
        deckComboBox.setOnAction((event) -> switchDecksAction.run());
        deckComboBox.disableProperty().bind(Bindings.createBooleanBinding(() -> deckComboBox.getItems().isEmpty(), deckComboBox.getItems()));
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
