package com.algosenpai.app.commands;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
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

import java.io.IOException;

public class HistoryCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryCommandTest.class.getResource("/view/MainWindow.fxml"));
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
    void testHistoryMousePress() {
        clickOn("#userInput").write("history");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("history", actualText);
    }

    @Test
    void testHistoryKeyPress() {
        clickOn("#userInput").write("history").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("history", actualText);
    }

    @Test
    void testHistoryWithSpace() {
        clickOn("#userInput").write(" history ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" history ", actualText);
    }

    @Test
    void testHistoryMissingInput() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("history");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. Please key in the number of commands "
                + "you'd like to view in the following format: e.g history 5", actualText);
    }

    @Test
    void testHistoryWrongTypeInput() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("history two");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. "
                                          + "Please key in a valid number of commands you'd like to view!", actualText);
    }

    @Test
    void testHistoryInvalidInputSize() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("history 2 3");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. Too many inputs entered!", actualText);
    }

    @Test
    void testHistoryLowerBoundary() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("history -1");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. "
                                          + "Please key in a valid number of commands you'd like to view!", actualText);
    }

    @Test
    void testHistoryWithLimitsUpperBoundary() throws IOException, FileParsingException {
        clickOn("#userInput").write("select sorting").clickOn("#sendButton");
        clickOn("#userInput").write("quiz").clickOn("#sendButton");
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("history 5");
        String actualText = command.execute();
        Assertions.assertEquals("OOPS!!! Error occurred. You don't have that many past commands!", actualText);
    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
