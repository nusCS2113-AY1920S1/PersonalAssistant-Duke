package com.algosenpai.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent fxmlSplashScreen = FXMLLoader.load(getClass().getResource("/view/SplashScreen.fxml"));
        Scene splashScreen = new Scene(fxmlSplashScreen, 600, 400);
        stage.setScene(splashScreen);
        stage.show();
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
        clickOn("#appTitle");
        FxAssert.verifyThat("#appTitle", TextMatchers.hasText("Welcome to AlgoSenpai Adventures!"));
    }

}