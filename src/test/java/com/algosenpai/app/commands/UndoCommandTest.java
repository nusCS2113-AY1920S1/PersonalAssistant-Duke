package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class UndoCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UndoCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats);
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
    void testUndoCommandNegativeUndos() {
        clickOn("#userInput").write("undo -1");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(0, numberOfMessages);
    }

    @Test
    void testUndoCommandPositiveUndos() {
        clickOn("#userInput").write("undo 1");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(0, numberOfMessages);
    }

    @Test
    void testUndoCommandMoreUndosThanMessage() {
        clickOn("#userInput").write("testing");
        clickOn("#sendButton");
        clickOn("#userInput").write("undo 5");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(0, numberOfMessages);
    }

    @Test
    void testUndoCommandLessUndosThanMessage() {
        clickOn("#userInput").write("testing");
        clickOn("#sendButton");
        clickOn("#userInput").write("undo 1");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(2, numberOfMessages);
    }

    @Test
    void testUndoCommandDefaultUndos() {
        clickOn("#userInput").write("testing");
        clickOn("#sendButton");
        clickOn("#userInput").write("undo");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(2, numberOfMessages);
    }

    @Test
    void testSoundLevelUpperBoundary() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("undo testing");
        String actualText = command.execute();
        Assertions.assertEquals("Not a valid number", actualText);
    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
