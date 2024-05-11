package com.kyloapps.deckeditor;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class DeckEditorDialog implements Builder<Region> {
    private Region content;
    private Region footer;
    private ModalPane modalPane;

    public DeckEditorDialog(ModalPane modalPane, Region content, Region footer) {
        this.modalPane = modalPane;
        this.content = content;
        this.footer = footer;
    }

    @Override
    public Region build() {
        BorderPane contentPane = new BorderPane();
        BorderPane.setMargin(contentPane, new Insets(40));
        contentPane.getStyleClass().add(Styles.BG_DEFAULT);
        contentPane.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px;");

        contentPane.setTop(createCloseButton());
        contentPane.setCenter(content);
        if (footer != null)
            contentPane.setBottom(footer);

        return new BorderPane(contentPane);
    }

    private Node createCloseButton() {
        Button closeButton = new Button("Close", new FontIcon(MaterialDesignC.CLOSE));
        BorderPane.setMargin(closeButton, new Insets(15));
        BorderPane.setAlignment(closeButton, Pos.TOP_RIGHT);
        closeButton.setOnAction((event) -> modalPane.hide());
        return closeButton;
    }
}
