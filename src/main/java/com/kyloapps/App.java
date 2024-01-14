package com.kyloapps;

import atlantafx.base.theme.PrimerDark;
import com.kyloapps.Controller.Controller;
import com.kyloapps.Model.AnswerChoice;
import com.kyloapps.Model.Deck;
import com.kyloapps.View.SimpleFlashcardView;
import com.kyloapps.View.TableFlashcardView;
import com.kyloapps.mainmvci.MainMvciController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainMvciController().getView(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(App.class);
    }
}