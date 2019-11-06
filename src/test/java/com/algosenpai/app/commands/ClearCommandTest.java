package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.components.DialogBox;
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

public class ClearCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClearCommandTest.class.getResource("/view/MainWindow.fxml"));
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
    void testClearCommand() {
        clickOn("#userInput").write("clear");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        Assertions.assertEquals(0, numberOfMessages);
    }

    @Test
    void testClearCommandAfterSomeInputs() {
        clickOn("#userInput").write("testing");
        clickOn("#sendButton");
        clickOn("#userInput").write("testing");
        clickOn("#sendButton");
        clickOn("#userInput").write("clear");
        clickOn("#sendButton");
        VBox container = find();
        int numberOfMessages = container.getChildren().size();
        //        Assertions.assertEquals(0, numberOfMessages);
    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
