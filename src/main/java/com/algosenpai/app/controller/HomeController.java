package com.algosenpai.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController extends ParentController {

    @FXML
    private Label sceneTitle;

    @FXML
    private Button nextButton;

    @FXML
    private TextField userInput;

    @FXML
    private Button settings;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = getStage(event);
        MusicController.playMusic("saturation.wav");
        changeScene(stage, "/view/quiz.fxml");
    }

}