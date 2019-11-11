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


public class InvalidCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(InvalidCommandTest.class.getResource("/view/MainWindow.fxml"));
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
    }

    @Test
    void testInvalidMousePress() {
        clickOn("#userInput").write("me nu");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("me nu", actualText);
    }

    @Test
    void testInvalidKeyPress() {
        clickOn("#userInput").write("printt").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("printt", actualText);
    }

    @Test
    void testInvalidWithSpace() {
        clickOn("#userInput").write(" menuu ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" menuu ", actualText);
    }

    @Test
    void testInvalidMultipleSpaces() throws IOException, FileParsingException {
        UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(previousStats);
        Command command = logic.executeCommand("qu iz");
        String actualText = command.execute();
        if (previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : "
                    + "'hello NAME GENDER (boy/girl)' please.", actualText);
        } else if (!previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("OOPS!!! Error occurred. Please input a valid command. "
                    + "Did you mean... quiz?", actualText);
        }
    }

    @Test
    void testInvalidStartWithInsteadOfEditDistance() throws IOException, FileParsingException {
        UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(previousStats);
        Command command = logic.executeCommand("ar");
        String actualText = command.execute();
        if (previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : "
                    + "'hello NAME GENDER (boy/girl)' please.", actualText);
        } else if (!previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("OOPS!!! Error occurred. Please input a valid command. Did you mean... "
                    + "arcade, archive?", actualText);
        }
    }

    @Test
    void testInvalidWithMinEditDistance() throws IOException, FileParsingException {
        UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(previousStats);
        Command command = logic.executeCommand("exig");
        String actualText = command.execute();
        if (previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : "
                    + "'hello NAME GENDER (boy/girl)' please.", actualText);
        } else if (!previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("OOPS!!! Error occurred. Please input a valid command. "
                    + "Did you mean... exit?", actualText);
        }
    }

    @Test
    void testInvalidWithCharacterAbsentFromAllCommands() throws IOException, FileParsingException {
        UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(previousStats);
        Command command = logic.executeCommand("f");
        String actualText = command.execute();
        if (previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : "
                    + "'hello NAME GENDER (boy/girl)' please.", actualText);
        } else if (!previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("OOPS!!! Error occurred. Please input a valid command. "
                    + "Enter `menu` to view our list of commands "
                    + "and `menu <command> to find out how to use them!", actualText);
        }
    }

    @Test
    void testInvalidWithStringThatContainsCharactersAbsentFromAllCommands() throws IOException, FileParsingException {
        UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(previousStats);
        Command command = logic.executeCommand("ffffggggjjjj");
        String actualText = command.execute();
        if (previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : "
                    + "'hello NAME GENDER (boy/girl)' please.", actualText);
        } else if (!previousStats.getUsername().equals("Default")) {
            Assertions.assertEquals("OOPS!!! Error occurred. Please input a valid command. "
                    + "Enter `menu` to view our list of commands "
                    + "and `menu <command> to find out how to use them!", actualText);
        }
    }

    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
