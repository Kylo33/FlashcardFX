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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class PracticeMvciViewBuilder implements Builder<Region> {
    private final PracticeMvciModel model;
    private final Runnable previousAction;
    private final Runnable nextAction;
    private final ObjectProperty<Node> currentCardNode = new SimpleObjectProperty<>();
    private final PracticeViewFlashcardVisitor practiceViewVisitor = new PracticeViewFlashcardVisitor();

    public PracticeMvciViewBuilder(PracticeMvciModel model, Runnable previousAction, Runnable nextAction) {
        this.model = model;
        this.previousAction = previousAction;
        this.nextAction = nextAction;
        currentCardNode.bind(Bindings.createObjectBinding(() -> {
            Deck currentDeck = model.getCurrentDeck();
            if (currentDeck == null) return null;
            Flashcard currentFlashcard = currentDeck.getFlashcards().get(model.getCurrentFlashcardIndex());
            return currentFlashcard.accept(practiceViewVisitor);
        }, model.currentFlashcardIndexProperty(), model.currentDeckProperty()));
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        Node cardContainer = createCardContainer();
        BorderPane.setMargin(cardContainer, new Insets(30));
        result.setCenter(cardContainer);
        result.setBottom(createNavigation());
        return result;
    }

    private Node createNavigation() {
        HBox result = new HBox();
        result.setAlignment(Pos.CENTER);

        Button previousCardButton = new Button(null, new FontIcon(MaterialDesignC.CHEVRON_LEFT));
        previousCardButton.getStyleClass().addAll(Styles.LEFT_PILL, Styles.LARGE);
        previousCardButton.setOnAction((event) -> previousAction.run());
        previousCardButton.disableProperty().bind(model.previousCardExistsProperty().not());

        Button nextCardButton = new Button(null, new FontIcon(MaterialDesignC.CHEVRON_RIGHT));
        nextCardButton.getStyleClass().addAll(Styles.RIGHT_PILL, Styles.LARGE);
        nextCardButton.setOnAction((event) -> nextAction.run());
        nextCardButton.disableProperty().bind(model.nextCardExistsProperty().not());

        result.getChildren().addAll(previousCardButton, nextCardButton);
        return result;
    }

    private Node createCardContainer() {
        StackPane result = new StackPane();
        result.getStyleClass().add("card-container");
        BorderPane cardHolder = new BorderPane();
        cardHolder.centerProperty().bind(currentCardNode);
        result.getChildren().add(cardHolder);
        return result;
    }
}
