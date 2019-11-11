package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.components.DialogBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;


public class HistoryNavigationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryNavigationTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats, false);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
        System.gc();
    }

    @Test
    void testHistoryNav_EmptyHistory_shouldNotCrash() {
        clickOn("#userInput")
                .press(KeyCode.UP)
                .press(KeyCode.UP)
                .press(KeyCode.DOWN)
                .press(KeyCode.DOWN)
                .press(KeyCode.UP)
                .press(KeyCode.DOWN);
    }

    @Test
    void testHistoryNav() {

        clickOn("#userInput").write("command 1").clickOn("#sendButton");
        clickOn("#userInput").write("command 2").clickOn("#sendButton");
        clickOn("#userInput").write("command 3").clickOn("#sendButton");
        clickOn("#userInput");


        TextField userInput = lookup("#userInput").query();

        type(KeyCode.UP);
        Assertions.assertEquals("command 3", userInput.getText());
        type(KeyCode.UP);
        Assertions.assertEquals("command 2", userInput.getText());
        type(KeyCode.UP);
        Assertions.assertEquals("command 1", userInput.getText());
        type(KeyCode.UP);
        Assertions.assertEquals("command 1", userInput.getText());
        type(KeyCode.DOWN);
        Assertions.assertEquals("command 2", userInput.getText());
        type(KeyCode.DOWN);
        Assertions.assertEquals("command 3", userInput.getText());
        type(KeyCode.DOWN);
        Assertions.assertEquals("", userInput.getText());



    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
