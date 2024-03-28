package com.kyloapps.deckeditor.cardeditor.forms.table;

import atlantafx.base.controls.Tile;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class TableMvciViewBuilder implements Builder<Region> {

    private final TableMvciModel model;
    private final ObjectProperty<Integer> columnSpinnerValueProperty;
    private final ObjectProperty<Integer> rowSpinnerValueProperty;

    public TableMvciViewBuilder(TableMvciModel model) {
        this.model = model;
        columnSpinnerValueProperty = model.columnCountProperty().asObject();
        rowSpinnerValueProperty = model.rowCountProperty().asObject();
    }

    @Override
    public Region build() {
        VBox optionBox = new VBox(15);
        Platform.runLater(() -> optionBox.getChildren().setAll(model.getOptionTiles()));
        model.getOptionTiles().addListener((InvalidationListener) observable
                -> Platform.runLater(
                () -> optionBox.getChildren().setAll(model.getOptionTiles())));
        return new VBox(15, model.getQuestionTile(), createRowSpinnerTile(), createColumnSpinnerTile(), createHeaderTile(), optionBox);
    }

    private Node createHeaderTile() {
        return model.getHeaders();
    }

    private Node createColumnSpinnerTile() {
        Tile result = new Tile("Columns", "Enter the number of columns in the table.");
        int currentColumnCount = model.getOptionTiles().isEmpty() ? 2 : model.getOptionTiles().size();
        Spinner<Integer> spinner = new Spinner<>(TableMvciModel.MIN_COLUMN_COUNT, TableMvciModel.MAX_COLUMN_COUNT, currentColumnCount);
        columnSpinnerValueProperty.bindBidirectional(spinner.getValueFactory().valueProperty());
        result.setAction(spinner);
        return result;
    }

    private Node createRowSpinnerTile() {
        Tile result = new Tile("Rows", "Enter the number of rows in the table.");
        Spinner<Integer> spinner = new Spinner<>(TableMvciModel.MIN_ROW_COUNT, TableMvciModel.MAX_ROW_COUNT, model.getOptionTiles().size());
        rowSpinnerValueProperty.bindBidirectional(spinner.getValueFactory().valueProperty());
        result.setAction(spinner);
        return result;
    }
}
