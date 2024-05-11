package com.kyloapps.practice;

import atlantafx.base.theme.Styles;
import com.kyloapps.domain.Flashcard;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class PracticeMvciViewBuilder implements Builder<Region> {

    private final PracticeMvciModel model;
    private final Runnable unloadAction;
    private static final AnswerCreatorVisitor answerCreatorVisitor = new AnswerCreatorVisitor();

    public PracticeMvciViewBuilder(PracticeMvciModel model, Runnable unloadAction) {
        this.model = model;
        this.unloadAction = unloadAction;
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

        GridPane.setHgrow(questionPane, Priority.ALWAYS);

        Node question = createQuestionLabel();
        GridPane.setVgrow(question, Priority.ALWAYS);
        GridPane.setHgrow(question, Priority.ALWAYS);
        questionPane.add(question, 0, 0);
        Region image = createImagePane();
        questionPane.add(image, 1,0);

        rootGridPane.add(questionPane, 0, 0);

        ObjectProperty<AnswerDisplay> answerDisplayObjectProperty = new SimpleObjectProperty<>();
        answerDisplayObjectProperty.bind(Bindings.createObjectBinding(() -> {
            if (model.getCurrentDeck() == null) return null;
            Flashcard currentFlashcard = model.getCurrentDeck().getFlashcards().get(model.getCurrentDeckIndex());
            return currentFlashcard.accept(answerCreatorVisitor);
        }, model.currentDeckProperty(), model.currentDeckIndexProperty()));

        HBox answerBox = new HBox();
        answerBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPaneContainer = new ScrollPane(answerBox);
        scrollPaneContainer.setFitToWidth(true);
        scrollPaneContainer.setFitToHeight(true);

        rootGridPane.add(scrollPaneContainer, 0, 1);
        GridPane.setVgrow(scrollPaneContainer, Priority.ALWAYS);

        rootGridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                answerDisplayObjectProperty.get().getForwardAction().run();
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY))
                answerDisplayObjectProperty.get().getBackAction().run();
        });

        answerDisplayObjectProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            if (answerBox.getChildren().isEmpty())
                answerBox.getChildren().add(answerDisplayObjectProperty.get().getAnswerRegion());
            else
                answerBox.getChildren().set(0, answerDisplayObjectProperty.get().getAnswerRegion());
        });

        result.getChildren().add(rootGridPane);
        return result;
    }

    private Region createImagePane() {
        Pane imagePane = new Pane();
        ProgressIndicator loadingIndicator = new ProgressIndicator();

        model.imageProperty().addListener((observable, oldImage, newImage) -> {
            // Set the background for the imagePane
            if (newImage == null) {
                imagePane.setBackground(null);
                return;
            }
            setImageBackground(imagePane, newImage);

            // Manage the state of the loading indicator based on whether the image is still loading.
            loadingIndicator.progressProperty().unbind();
            loadingIndicator.progressProperty().bind(Bindings.createDoubleBinding(() -> {
                return newImage.isBackgroundLoading() ? -1d : 0d;
            }, newImage.progressProperty()));
        });

        StackPane result = new StackPane(loadingIndicator, imagePane);
        result.setAlignment(Pos.CENTER);
        GridPane.setHgrow(result, Priority.ALWAYS);

        result.visibleProperty().bind(Bindings.createBooleanBinding(() -> model.getImage() == null, model.imageProperty()).not());

        return result;
    }

    private void setImageBackground(Pane imagePane, Image newImage) {
        imagePane.setBackground(new Background(new BackgroundImage(newImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
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
            if (!isVisible) unloadAction.run();
        });
    }
}
