package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SceneController implements Initializable {

    @FXML
    Text sceneTitle;

    @FXML
    private TextField userInput;

    public HomeController() {
        handle();
    }

    private int userInputY = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Welcome to AlgoSenpai Adventures!");
        setNodePos(sceneTitle, 0, 0);
        setTextStyle(sceneTitle, 237, 16, 160, true);
        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);
    }

    private void handle() {
        AnimationTimerController backgroundSceneTimer = new AnimationTimerController(1) {
            @Override
            public void handle() {
                if (userInputY <= 250) {
                    userInputY += 1;
                    setNodePos(sceneTitle, userInputY, 0);

                }
            }
        };
        backgroundSceneTimer.start();
    }

    /**
     * Handle shortcut key  inputs.
     * @param keyEvent key inputs.
     * @throws IOException key input error.
     */
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.Q) {
            MusicController.playMusic("saturation.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);

            changeScene("quiz.fxml", imageName);
            //changeScene(ResourcePathConstant.viewResourcePath + "quiz.fxml");
        }
        if (keyEvent.getCode() == KeyCode.R) {
            MusicController.playMusic("saturation.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);

            changeScene("review.fxml", imageName);
            //changeScene(ResourcePathConstant.viewResourcePath + "review.fxml");
        }
        if (keyEvent.getCode() == KeyCode.G) {
            MusicController.playMusic("saturation.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);

            changeScene("girls.fxml", imageName);
            //changeScene(ResourcePathConstant.viewResourcePath + "girls.fxml");
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            userInput.getParent().requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.M) {
            toggleVolume();
        }
    }

    /**
     * Handle mouse clicking inputs.
     * @param mouseEvent mouse inputs.
     */
    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        if (!userInput.equals(mouseEvent.getSource())) {
            userInput.getParent().requestFocus();
        }
    }

    public String getSceneTitle() {
        return sceneTitle.getText();
    }
}