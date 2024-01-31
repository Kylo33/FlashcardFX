package com.kyloapps.deckeditor;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.domain.Deck;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class DeckEditorMvciViewBuilder implements Builder<Region> {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciViewBuilder(DeckEditorMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        result.setTop(new VBox(createGeneralDeckEditor(), new Separator(Orientation.HORIZONTAL)));
        return result;
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
        result.setAction(deleteButton);
        return result;
    }

    private Node createDeckCreator() {
        Tile result = new Tile("Create Deck", "Create a new deck.");
        Button createButton = new Button("Create", new FontIcon(MaterialDesignP.PLUS));
        createButton.getStyleClass().add(Styles.SUCCESS);
        result.setAction(createButton);
        return result;
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
        ChoiceBox<Deck> deckChoiceBox = new ChoiceBox<>();
        result.setAction(deckChoiceBox);
        return result;
    }
}
