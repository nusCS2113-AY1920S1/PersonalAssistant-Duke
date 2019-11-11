package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.components.DialogBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class HelpCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelpCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats,false);
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
    void testHelpKeyPress() {
        clickOn("#userInput").write("help sorting").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("help sorting", actualText);
    }
    


    @Test
    void testHelpWithSpace() {
        clickOn("#userInput").write(" help sorting ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" help sorting ", actualText);
    }

    @Test
    void testHelpOutputChapter1() {
        clickOn("#userInput").write("help sorting").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Try solving these problems on Kattis:\n"
                + "lineup, mjehuric, sidewayssorting", actualText);
    }

    @Test
    void testHelpOutputChapter2() {
        clickOn("#userInput").write("help linkedlist").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Try solving these problems on Kattis:\n"
                + "evenup, pairingsocks, coconut", actualText);
    }

    @Test
    void testHelpOutputChapter3() {
        clickOn("#userInput").write("help bitmask").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Try solving these problems on Kattis:\n"
                + "committeeassignment, pebblesolitaire", actualText);
    }

    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
