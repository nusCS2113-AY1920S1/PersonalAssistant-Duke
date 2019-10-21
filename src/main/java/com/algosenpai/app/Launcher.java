package com.algosenpai.app;

import com.algosenpai.app.controller.SceneController;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(SceneController.class, args);


    }
}
