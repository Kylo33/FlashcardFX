package com.kyloapps.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class Menu extends ScrollPane {
    private static int GAP_SIZE = 30;
    public FlowPane flowPane;

    public Menu() {
        // Create and set up the FlowPane
        flowPane = new FlowPane();
        flowPane.setHgap(GAP_SIZE);
        flowPane.setVgap(GAP_SIZE);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(GAP_SIZE));

        // Set up the ScrollPane
        setContent(flowPane);
        setFitToWidth(true);
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }
}
