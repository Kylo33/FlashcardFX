package com.kyloapps;

import atlantafx.base.controls.RingProgressIndicator;
import atlantafx.base.theme.Styles;
import com.kyloapps.Model.AnswerChoice;
import com.kyloapps.View.ImageBox;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public abstract class DisplayableFlashcard<T> {
    private String question;
    private List<AnswerChoice<T>> options;
    private IntegerProperty currentStep = new SimpleIntegerProperty(0);
    private ImageBox imageBox;
    private String imageURL;
    private ImageView imageView = new ImageView();
    private GridPane container = new GridPane();
    ChangeListener<Number> listenerForPane;

    public DisplayableFlashcard(String question, List<AnswerChoice<T>> options, String imageURL) {
        this.question = question;
        this.options = options;
        this.currentStep.addListener((obs, oldV, newV) -> {
            applyStep((int) newV);
        });
        this.imageURL = imageURL;
        imageView.setPreserveRatio(true);
        imageView.setOpacity(0.5);

        container.setVgap(30);
        Platform.runLater(() -> {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(50d);
            container.getRowConstraints().add(rowConstraints);
        });
        container.getChildren().addListener((ListChangeListener<? super Node>) (change) -> {
            container.getChildren().forEach((child) -> {
                GridPane.setVgrow(child, Priority.ALWAYS);
                GridPane.setHgrow(child, Priority.ALWAYS);
            });
        });
        container.setAlignment(Pos.CENTER);

        container.add(buildQuestionBox(), 0, 0);
    }

    private GridPane buildQuestionBox() {

        // Create a GridPane for the question and image.
        GridPane questionBox = new GridPane();
        questionBox.setHgap(30);

        // Add the question label.
        Label questionLabel = new Label(question);
        questionBox.add(questionLabel, 0, 0);
        GridPane.setHalignment(questionLabel, HPos.CENTER);

        if (imageURL != null) {

            Platform.runLater(() -> {
                // Add column constraints to give each column equal width.
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(50.0);
                questionBox.getColumnConstraints().add(cc);
            });

            // Outer StackPane to center the innerImagePane.
            imageBox = new ImageBox();
            questionBox.add(imageBox, 1, 0);
        }

        // Make all items in the GridPane grow to fill their cell.
        questionBox.getChildren().forEach((child) -> {
            GridPane.setVgrow(child, Priority.ALWAYS);
            GridPane.setHgrow(child, Priority.ALWAYS);
        });

        return questionBox;
    }

    public GridPane getContainer() {
        return container;
    }

    public void setContainer(GridPane container) {
        this.container = container;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerChoice<T>> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerChoice<T>> options) {
        this.options = options;
    }

    public Node display() {
        return container;
    }

    public abstract String getTypeString();

    public void load() {
        if (imageURL != null) {
            new Thread(() -> {
                    URLConnection urlConnection;
                    Image image;
                    try {
                        urlConnection = new URL(imageURL).openConnection();
                        urlConnection.setRequestProperty("User-Agent",
                                "Mozilla/5.0 (X11; Linux x86_64; rv:121.0) Gecko/20100101 Firefox/121.0");
                        image = new Image(urlConnection.getInputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(() -> imageBox.setImage(image));
            }).start();
        }
    }

    public abstract void applyStep(int newVal);
    public abstract int getStepCount();

    public void nextStep() {
        if (currentStep.get() < getStepCount() - 1) {
            currentStep.set(currentStep.get() + 1);
        }
    }

    public void backStep() {
        if (currentStep.get() > 0) {
            currentStep.set(currentStep.get() - 1);
        }
    }

    public void resetDisplay() {
        currentStep.set(0);
    }

    public void checkStep(int newVal) {
        if (newVal > getStepCount() || newVal < 0) {
            throw new IllegalStateException(newVal + " is out of bounds.");
        }
    }


}