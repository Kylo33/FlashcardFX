package com.kyloapps.practice;

import atlantafx.base.theme.Styles;
import com.kyloapps.domain.Deck;
import com.kyloapps.domain.Flashcard;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class PracticeMvciViewBuilder implements Builder<Region> {

    private final PracticeMvciModel model;
    private final Runnable onVisibleAction;

    public PracticeMvciViewBuilder(PracticeMvciModel model, Runnable onVisibleAction) {
        this.model = model;
        this.onVisibleAction = onVisibleAction;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        Node cardContainer = createCardContainer();
        BorderPane.setMargin(cardContainer, new Insets(30));
        result.setCenter(cardContainer);
        result.setBottom(createNavigation());

        addVisibleListener(result);
        return result;
    }

    private Node createNavigation() {
        HBox result = new HBox();
        result.setAlignment(Pos.CENTER);

        Button previousCardButton = new Button(null, new FontIcon(MaterialDesignC.CHEVRON_LEFT));
        previousCardButton.getStyleClass().addAll(Styles.LEFT_PILL, Styles.LARGE);
        previousCardButton.disableProperty().bind(model.prevExistsProperty().not());
        previousCardButton.setOnAction((event) -> model.setCurrentDeckIndex(model.getCurrentDeckIndex() - 1));

        Button nextCardButton = new Button(null, new FontIcon(MaterialDesignC.CHEVRON_RIGHT));
        nextCardButton.getStyleClass().addAll(Styles.RIGHT_PILL, Styles.LARGE);
        nextCardButton.disableProperty().bind(model.nextExistsProperty().not());
        nextCardButton.setOnAction((event) -> model.setCurrentDeckIndex(model.getCurrentDeckIndex() + 1));

        result.getChildren().addAll(previousCardButton, nextCardButton);
        return result;
    }

    private Node createCardContainer() {
        StackPane result = new StackPane();
        result.getStyleClass().add("card-container");

        GridPane rootGridPane = new GridPane();
        GridPane questionPane = new GridPane();

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(50);
        rootGridPane.getRowConstraints().add(rc);

        ColumnConstraints cc = new ColumnConstraints();
        cc.percentWidthProperty().bind(Bindings.createIntegerBinding(() -> model.getImage() == null ? 100 : 50, model.imageProperty()));
        questionPane.getColumnConstraints().add(cc);

        rootGridPane.setGridLinesVisible(true);
        questionPane.setGridLinesVisible(true);

        GridPane.setHgrow(questionPane, Priority.ALWAYS);

        Node question = createQuestionLabel();
        GridPane.setVgrow(question, Priority.ALWAYS);
        GridPane.setHgrow(question, Priority.ALWAYS);
        questionPane.add(question, 0, 0);

        rootGridPane.add(questionPane, 0, 0);

        result.getChildren().add(rootGridPane);
        return result;
    }

    private Node createQuestionLabel() {
        Label l = new Label();
        l.textProperty().bind(model.questionProperty());
        l.setTextAlignment(TextAlignment.CENTER);
        l.setWrapText(true);

        StackPane result = new StackPane(l);
        result.setAlignment(Pos.CENTER);
        return result;
    }

    private void addVisibleListener(Region result) {
        result.visibleProperty().addListener((observable, wasVisible, isVisible) -> {
            if (isVisible) onVisibleAction.run();
        });
    }
}
