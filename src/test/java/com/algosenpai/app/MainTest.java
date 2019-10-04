package com.algosenpai.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class MainTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/view/MainWindow.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @BeforeAll
    static void setUp() throws Exception {
    }

    @AfterAll
    static void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testUserInput() {
        clickOn("#userInput");
        write("This is a test!");
        clickOn("#sendButton");
    }

}