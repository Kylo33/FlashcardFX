package com.kyloapps.deckeditor;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.domain.Deck;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Builder;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class DeckEditorMvciViewBuilder implements Builder<Region> {
    private static final int MAX_DECK_NAME_LENGTH = 10;
    private final DeckEditorMvciModel model;
    private final ModalPane modalPane = new ModalPane();
    private final Runnable deleteDeckAction;
    private final Runnable createDeckAction;

    public DeckEditorMvciViewBuilder(DeckEditorMvciModel model, Runnable createDeckAction, Runnable deleteDeckAction) {
        this.model = model;
        this.createDeckAction = createDeckAction;
        this.deleteDeckAction = deleteDeckAction;
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
        VBox cardEditors = new VBox();
        result.setContent(new VBox(cardEditors, createNewCardRegion()));
        return result;
    }

    private Region createNewCardRegion() {
        Button newCardButton = new Button("New Card", new FontIcon(MaterialDesignP.PLUS));
        HBox buttonWrapper = new HBox(newCardButton);
        buttonWrapper.setAlignment(Pos.CENTER);
        newCardButton.getStyleClass().add(Styles.SUCCESS);
        return new VBox(
                new Separator(Orientation.HORIZONTAL),
                buttonWrapper
        );
    }

    /**
     * Creates a grid of menu options related to entire decks. (Creating, deleting, switching, and renaming decks)
     *
     * @return Node with the options in it
     */
    private Node createGeneralDeckEditor() {
        GridPane result = new GridPane();
        result.add(createDeckSwitcher(), 0, 0);
        result.add(createDeckRenamer(), 0, 1);
        result.add(createDeckCreator(), 1, 0);
        result.add(createDeckDeleter(), 1, 1);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50d);
        result.getColumnConstraints().add(columnConstraints);

        result.getChildren().stream().forEach(child -> {
            GridPane.setHgrow(child, Priority.ALWAYS);
        });

        return result;
    }

    private Node createDeckDeleter() {
        Tile result = new Tile("Delete Deck", "Delete the deck that is selected.");
        Button deleteButton = new Button("Delete", new FontIcon(MaterialDesignT.TRASH_CAN));
        deleteButton.getStyleClass().add(Styles.DANGER);
        deleteButton.setOnAction(event -> deleteDeckAction.run());
        model.setCurrentDeck(null);
        result.setAction(deleteButton);
        return result;
    }

    private Node createDeckCreator() {
        Tile result = new Tile("Create Deck", "Create a new deck.");
        Button createButton = new Button("Create", new FontIcon(MaterialDesignP.PLUS));
        createButton.getStyleClass().add(Styles.SUCCESS);
        result.setAction(createButton);

        createButton.setOnAction(event -> showCreationDialog());

        return result;
    }

    private void showCreationDialog() {
        BorderPane result = new BorderPane();
        BorderPane content = new BorderPane();
        BorderPane.setMargin(content, new Insets(40));
        content.getStyleClass().add(Styles.BG_DEFAULT);
        result.setCenter(content);

        Button closeButton = new Button("Close", new FontIcon(MaterialDesignC.CLOSE));
        BorderPane.setMargin(closeButton, new Insets(15));
        BorderPane.setAlignment(closeButton, Pos.TOP_RIGHT);
        closeButton.setOnAction((event) -> modalPane.hide());
        content.setTop(closeButton);

        Button createButton = new Button("Create Deck", new FontIcon(MaterialDesignP.PLUS));
        createButton.getStyleClass().add(Styles.SUCCESS);
        BorderPane.setMargin(createButton, new Insets(15));
        BorderPane.setAlignment(createButton, Pos.BOTTOM_RIGHT);
        createButton.setOnAction((event) -> {
            createDeckAction.run();
            modalPane.hide();
        });
        content.setBottom(createButton);

        Node center = getCreationDialogContent();
        content.setCenter(center);

        modalPane.show(result);
    }

    private Node getCreationDialogContent() {
        Tile titleTile = new Tile("Deck Title", "Give your deck a descriptive name!");
        TextField titleField = new TextField();
        titleField.textProperty().bindBidirectional(model.newDeckNameProperty());
        titleTile.setAction(titleField);

        Tile descriptionTile = new Tile("Deck Description", "Give your deck a description.");
        TextField descriptionField = new TextField();
        descriptionField.textProperty().bindBidirectional(model.newDeckDescriptionProperty());
        descriptionTile.setAction(descriptionField);

        VBox node = new VBox(15, titleTile, new Separator(Orientation.HORIZONTAL), descriptionTile);
        node.setPadding(new Insets(15));
        return node;
    }

    private Node createDeckRenamer() {
        Tile result = new Tile("Edit Deck Details", "Edit the details of the current deck.");
        Button renameButton = new Button("Rename", new FontIcon(MaterialDesignF.FORM_TEXTBOX));
        Button descriptionEditButton = new Button("Change Description", new FontIcon(MaterialDesignF.FORMAT_ALIGN_LEFT));
        result.setAction(new HBox(15, renameButton, descriptionEditButton));
        return result;
    }

    private Node createDeckSwitcher() {
        Tile result = new Tile("Switch Decks", "Choose another deck to edit.");
        ChoiceBox<Deck> deckChoiceBox = new ChoiceBox<>(model.getDecks());
        deckChoiceBox.valueProperty().bindBidirectional(model.currentDeckProperty());
        result.setAction(deckChoiceBox);

        deckChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Deck deck) {
                if (deck == null)
                    return null;
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

        return result;
    }
}
