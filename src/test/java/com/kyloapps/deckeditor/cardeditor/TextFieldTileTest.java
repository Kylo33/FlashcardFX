package com.kyloapps.deckeditor.cardeditor;

import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class TextFieldTileTest {

    @Before
    public void setUp() {
        Platform.startup(() -> {});
    }

    @Test
    public void testEquals() {
        TextFieldTile textFieldTile1 = new TextFieldTile("Title", "Description");
        TextFieldTile textFieldTile2 = new TextFieldTile("Title", "Description");
        assertEquals(textFieldTile1, textFieldTile2);
    }
}