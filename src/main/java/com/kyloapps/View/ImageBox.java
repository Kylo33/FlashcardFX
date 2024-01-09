package com.kyloapps.View;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.stream.Stream;

public class ImageBox extends StackPane {
    private StackPane innerImagePane = new StackPane();
    private DoubleProperty imageRatio = new SimpleDoubleProperty(1);
    private ProgressIndicator loadingIndicator = new ProgressIndicator();
    private ChangeListener imageSizeKeeper;
    private static final BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);

    public ImageBox() {
        setAlignment(Pos.CENTER);
        getChildren().add(innerImagePane);

        innerImagePane.getChildren().add(loadingIndicator);
        innerImagePane.setAlignment(Pos.CENTER);
        innerImagePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        manageLoadingIndicator();
        manageImageRatio();
        preserveInnerImagePaneRatio();

        imageSizeKeeper = getImageSizeKeeper();
        Stream.of(widthProperty(), heightProperty(), innerImagePane.backgroundProperty()).forEach((property) -> {
            property.addListener(imageSizeKeeper);
        });
    }

    public void setImage(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, backgroundSize);
        innerImagePane.setBackground(new Background(backgroundImage));
    }

    private void preserveInnerImagePaneRatio() {
        innerImagePane.widthProperty().addListener((obs, oldValue, newValue) -> {
            innerImagePane.setPrefHeight(newValue.doubleValue() * Math.pow(imageRatio.get(), -1));
        });
    }

    private ChangeListener getImageSizeKeeper() {
        return (obs, oldValue, newValue) -> {
            Platform.runLater(() -> {
                double possibleWidth = getHeight() * imageRatio.get();
                if (possibleWidth <= getWidth()) {
                    innerImagePane.setPrefWidth(possibleWidth);
                } else {
                    innerImagePane.setPrefWidth(getWidth());
                }
            });
        };
    }

    private void manageImageRatio() {
        innerImagePane.backgroundProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                Image image = newValue.getImages().get(0).getImage();
                imageRatio.set(image.getWidth() / image.getHeight());
            } else imageRatio.set(1d);
        });
    }

    private void manageLoadingIndicator() {
        innerImagePane.backgroundProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                loadingIndicator.setVisible(false);
                loadingIndicator.setProgress(0);
            } else {
                loadingIndicator.setVisible(true);
                loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            }
        });
    }
}
