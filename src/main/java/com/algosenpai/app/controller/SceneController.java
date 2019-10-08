package com.algosenpai.app.controller;


import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.ImagesEnum;
import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends Application {

    @FXML
    protected Label sceneText;

    private static MusicController musicController;

    private static Stage stage;

    private static Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        SceneController.stage = stage;
        SceneController.musicController = new MusicController();
        String imageName = ImagesConstant.startAppImages.get(ImagesEnum.START_APP_2);
        changeScene("home.fxml", imageName);
    }

    private static Stage getStage() {
        return stage;
    }

    static void toggleVolume() {
        MusicController.toggleVolume();
    }

    public static void setRoot(Pane root) {
        SceneController.root = root;
    }

    void changeScene(String sceneName, String imageName) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource(ResourcePathConstant.viewResourcePath + sceneName));
        SceneController.root = root;
        Scene scene = new Scene(root, JavaFxConstant.windowWidth, JavaFxConstant.windowHeight);
        (SceneController.getStage()).setScene(scene);
        stage.setResizable(false);
        stage.show();
        changeBackgroundImage(ResourcePathConstant.imagesResourcePath + imageName);
    }

    void changeBackgroundImage(String imageName) {
        String fxBackgroundImageStyle = getFxBackgroundImageStyle(imageName);
        root.setStyle(fxBackgroundImageStyle);
    }

    private String getFxBackgroundImageStyle(String imageName) {
        return "-fx-background-image: url('" + imageName + "'); -fx-background-size: cover;";
    }

    void setNodePos(Node node, double downShift, double rightShift) {
        node.applyCss();
        final double width = node.getLayoutBounds().getWidth();
        node.setTranslateX((double) JavaFxConstant.windowWidth / 2 - width / 2 + rightShift);
        node.setTranslateY(downShift);
    }

    void setTextStyle(Node node, int red, int green, int blue, boolean bold, int fontSize, String fontStyle) {
        node.applyCss();
        node.setStyle("-fx-fill:rgb(" + red + "," + green + "," + blue + "); -fx-font: " + fontSize +
                " " + fontStyle + ";" + (bold ? "-fx-font-weight:bold;" : ""));
    }
}