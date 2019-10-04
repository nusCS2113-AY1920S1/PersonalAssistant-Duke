package com.algosenpai.app;

import com.algosenpai.app.controller.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

public class SceneControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(SceneController.class.getResource("/view/home.fxml"));
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
        FxAssert.verifyThat("#sceneTitle", LabeledMatchers.hasText("Home Scene"));
    }

}