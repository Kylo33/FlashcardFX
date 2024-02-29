package com.kyloapps;

import com.kyloapps.deckeditor.cardeditor.TextFieldTileAnswerOption;
import com.kyloapps.mainmvci.MainMvciController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    // Need a reference here to protect the controller and everything it references from garbage collection.
    private final MainMvciController controller = new MainMvciController();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(controller.getView(), 1280, 720);
        stage.setScene(scene);
        scene.getStylesheets().add("custom.css");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(App.class);
    }
}