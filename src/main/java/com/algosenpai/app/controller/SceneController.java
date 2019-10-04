package com.algosenpai.app.controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController extends Application {

    MusicController musicController;

    @Override
    public void start(Stage stage) throws Exception {
        musicController = new MusicController();
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}