package com.algosenpai.app.controller;


import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends Application {

    private static MusicController musicController;

    private static Stage stage;

    private static Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        SceneController.stage = stage;
        SceneController.musicController = new MusicController();
        SceneController.root = FXMLLoader.load(getClass().getResource(
                ResourcePathConstant.viewResourcePath + "home.fxml"));
        Scene scene = new Scene(root, JavaFxConstant.windowWidth, JavaFxConstant.windowHeight);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static Stage getStage() {
        return stage;
    }

    public static void setRoot(Pane root) {
        SceneController.root = root;
    }

    void changeScene(String sceneName) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource(sceneName));
        SceneController.root = root;
        Scene scene = new Scene(root, JavaFxConstant.windowWidth, JavaFxConstant.windowHeight);
        (SceneController.getStage()).setScene(scene);
        stage.show();
    }

    void changeBackgroundImage(String imageName) {
        String fxBackgroundImageStyle = getFxBackgroundImageStyle(imageName);
        root.setStyle(fxBackgroundImageStyle);
    }

    private String getFxBackgroundImageStyle(String imageName) {
        return "-fx-background-image: url('" + imageName + "'); -fx-background-size: cover;";
    }
}