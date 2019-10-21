package com.algosenpai.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;

public class SceneControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Pane mainNode = FXMLLoader.load(SceneController.class.getResource("/view/home.fxml"));
        SceneController.setRoot(mainNode);
        stage.setScene(new Scene(mainNode, JavaFxConstant.windowWidth, JavaFxConstant.windowHeight));
        stage.show();
        stage.toFront();
    }

    @BeforeAll
    static void setUp() {
    }

    @AfterAll
    static void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testInterfaceRendering() {
        clickOn("#sceneTitle");
        FxAssert.verifyThat("#sceneTitle", TextMatchers.hasText("Welcome to AlgoSenpai Adventures!"));
    }

}