package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
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

public class MenuCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        Logic logic = new Logic();
        fxmlLoader.<Ui>getController().setLogic(logic);
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
    }

    @Test
    void testMenuMousePress() {
        clickOn("#userInput").write("menu");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuKeyPress() {
        clickOn("#userInput").write("menu").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuWithSpace() {
        clickOn("#userInput").write(" menu ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" menu ", actualText);
    }

    @Test
    void testMenuOutput() {
        clickOn("#userInput").write("menu").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Senpai will teach you! Try these commands\n"
                + "MENU\n"
                + "START\n"
                + "SELECT\n"
                + "RESULT\n"
                + "REPORT\n"
                + "BACK\n"
                + "HISTORY\n"
                + "UNDO\n"
                + "CLEAR\n"
                + "RESET\n"
                + "SAVE\n"
                + "HELP\n"
                + "EXIT\n"
                + "PRINT\n"
                + "ARCHIVE\n"
                + "INVALID\n", actualText);
    }

    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}