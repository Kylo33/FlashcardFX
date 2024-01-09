package com.kyloapps;

import com.kyloapps.Controller.Controller;
import com.kyloapps.Model.AnswerChoice;
import com.kyloapps.Model.Deck;
import com.kyloapps.View.SimpleFlashcardView;
import com.kyloapps.View.TableFlashcardView;
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
        Controller controller = new Controller();
        Scene scene = new Scene(controller.getView(), 1280, 720);
        controller.initTheme();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(App.class);
    }
}