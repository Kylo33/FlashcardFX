package com.kyloapps.practice;

import atlantafx.base.theme.Styles;
import com.kyloapps.domain.Deck;
import com.kyloapps.domain.Flashcard;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        questionPane.setGridLinesVisible(true);

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

        result.getChildren().add(rootGridPane);
        return result;
    }

    private Region createImagePane() {
        Pane result = new Pane();
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.imageProperty().bind(model.imageProperty());

        imageView.fitWidthProperty().bind(result.widthProperty());

        ChangeListener<Number> onLoadListener = (observable, oldProgress, newProgress) -> {
            if (newProgress.doubleValue() == 1d) {
                double imageRatio = getRatio(imageView);
                if (result.getHeight() > predictHeight(result.getWidth(), imageRatio))
                    imageView.fitWidthProperty().bind(result.widthProperty());
                else
                    imageView.fitHeightProperty().bind(result.heightProperty());
            }
        };

        imageView.imageProperty().addListener((observable, oldImage, newImage) -> {
            if (oldImage != null)
                oldImage.progressProperty().removeListener(onLoadListener);
            if (newImage != null)
                newImage.progressProperty().addListener(onLoadListener);
        });

        result.widthProperty().addListener((observable, oldPaneWidth, newPaneWidth) -> {
            double imageRatio = getRatio(imageView);
            if (result.getHeight() > predictHeight(newPaneWidth.doubleValue(), imageRatio)) {
                unbindImageView(imageView);
                imageView.fitWidthProperty().bind(result.widthProperty());
                System.out.println("width");
            } else {
                unbindImageView(imageView);
                imageView.fitHeightProperty().bind(result.heightProperty());
                System.out.println("height");
            }
        });

        result.heightProperty().addListener((observable, oldPaneHeight, newPaneHeight) -> {
            double imageRatio = getRatio(imageView);
            if (result.getWidth() > predictWidth(newPaneHeight.doubleValue(), imageRatio)) {
                unbindImageView(imageView);
                imageView.fitHeightProperty().bind(result.heightProperty());
                System.out.println("height");
            } else {
                unbindImageView(imageView);
                imageView.fitWidthProperty().bind(result.widthProperty());
                System.out.println("width");
            }
        });

        ProgressIndicator loadingIndicator = new ProgressIndicator();

        imageView.imageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            loadingIndicator.progressProperty().unbind();
            loadingIndicator.progressProperty().bind(Bindings.createDoubleBinding(() -> {
                    return newValue.isBackgroundLoading() ? -1d : 0d;
            }, newValue.progressProperty()));

        });

        StackPane stackPane = new StackPane(loadingIndicator, imageView);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.prefWidthProperty().bind(result.widthProperty());
        stackPane.prefHeightProperty().bind(result.heightProperty());
        stackPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        result.getChildren().add(stackPane);

        GridPane.setHgrow(result, Priority.ALWAYS);
        return result;
    }

    private static void unbindImageView(ImageView imageView) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();
    }

    // imageRatio = width / height
    private double predictHeight(double width, double imageRatio) {
        return width / imageRatio;
    }

    // imageRatio = width / height
    private double predictWidth(double height, double imageRatio) {
        return imageRatio * height;
    }

    private double getRatio(ImageView imageView) {
        if (imageView.getImage() == null) return 0;
        return imageView.getImage().getWidth() / imageView.getImage().getHeight();
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
