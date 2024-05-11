package com.kyloapps.deckeditor.cardeditor.forms.table;

import atlantafx.base.controls.Tile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.deckeditor.cardeditor.forms.classic.ClassicMvciViewBuilder;
import com.tobiasdiez.easybind.EasyBind;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class TableMvciViewBuilder implements Builder<Region> {

    private final TableMvciModel model;
    private final ObjectProperty<Integer> columnSpinnerValueProperty;
    private final ObjectProperty<Integer> rowSpinnerValueProperty;
    private final ObservableList<Node> optionTiles;

    public TableMvciViewBuilder(TableMvciModel model) {
        this.model = model;
        this.columnSpinnerValueProperty = model.columnCountProperty().asObject();
        this.rowSpinnerValueProperty = model.rowCountProperty().asObject();
        this.optionTiles = bindOptionTiles();
    }

    private ObservableList<Node> bindOptionTiles() {
        return EasyBind.mapBacked(model.getOptions(), observableListAnswerOption -> {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption(
                    "Answer Option", "Enter an answer choice.", observableListAnswerOption.getContent());
            result.setCorrect(observableListAnswerOption.isCorrect());
            observableListAnswerOption.correctProperty().bindBidirectional(result.correctProperty());
            return result;
        });
    }

    @Override
    public Region build() {
        return new VBox(15,
                ClassicMvciViewBuilder.createQuestionTile(model.questionProperty()),
                ClassicMvciViewBuilder.createImageTile(model.imageUrlProperty()),
                createRowSpinnerTile(),
                createColumnSpinnerTile(),
                new TextFieldTile("Headers", "Labels above each row of the table.", model.getHeaders()),
                createOptionBox());
    }

    private Node createOptionBox() {
        VBox result = new VBox(15);
        result.getChildren().setAll(optionTiles);
        optionTiles.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next())
                result.getChildren().setAll(change.getList());
        });
        return result;
    }

    private Node createColumnSpinnerTile() {
        Tile result = new Tile("Columns", "Enter the number of columns in the table.");
        Spinner<Integer> spinner = new Spinner<>(TableMvciModel.MIN_COLUMN_COUNT, TableMvciModel.MAX_COLUMN_COUNT, model.getColumnCount());
        columnSpinnerValueProperty.bindBidirectional(spinner.getValueFactory().valueProperty());
        result.setAction(spinner);
        return result;
    }

    private Node createRowSpinnerTile() {
        Tile result = new Tile("Rows", "Enter the number of rows in the table.");
        Spinner<Integer> spinner = new Spinner<>(TableMvciModel.MIN_ROW_COUNT, TableMvciModel.MAX_ROW_COUNT, model.getRowCount());
        rowSpinnerValueProperty.bindBidirectional(spinner.getValueFactory().valueProperty());
        result.setAction(spinner);
        return result;
    }
}
