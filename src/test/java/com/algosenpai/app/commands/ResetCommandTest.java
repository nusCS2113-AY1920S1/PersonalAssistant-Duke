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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class ResetCommandTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ResetCommandTest.class.getResource("/view/MainWindow.fxml"));
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
    void testResetMousePress() {
        clickOn("#userInput").write("reset");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("reset", actualText);
    }


    @Test
    void testResetKeyPress() {
        clickOn("#userInput").write("reset").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("reset", actualText);
    }

    @Test
    void testResetWithSpace() {
        clickOn("#userInput").write(" reset ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" reset ", actualText);
    }


    @Test
    void testReset() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("reset");
        String actualText = command.execute();
        if (stats.getUserExp() == 0) {
            Assertions.assertEquals("OOPS!!! Error occurred. Your data has already been reset.", actualText);
        } else if (stats.getUserExp() != 0) {
            Assertions.assertEquals("Are you sure you want to reset your progress? (y/n)", actualText);
        }
    }


    @Test
    void testResetWithUserConfirmation() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("reset");
        if (stats.getUserExp() == 0) {
            String actualText = command.execute();
            Assertions.assertEquals("OOPS!!! Error occurred. Your data has already been reset.", actualText);
        } else if (stats.getUserExp() != 0) {
            clickOn("#userInput").write("y").clickOn("#sendButton");
            String actualText = command.execute();
            Assertions.assertEquals("You progress has been reset!", actualText);
        }
    }

    @Test
    void testResetAndAbort() throws IOException, FileParsingException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("reset");
        if (stats.getUserExp() == 0) {
            String actualText = command.execute();
            Assertions.assertEquals("OOPS!!! Error occurred. Your data has already been reset.", actualText);
        } else if (stats.getUserExp() != 0) {
            clickOn("#userInput").write("n").clickOn("#sendButton");
            String actualText = command.execute();
            Assertions.assertEquals("Reset operation cancelled!", actualText);
        }
    }



    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
