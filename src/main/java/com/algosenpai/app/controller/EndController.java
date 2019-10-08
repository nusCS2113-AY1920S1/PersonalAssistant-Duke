package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.ImagesEnum;
import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndController extends SceneController implements Initializable {

    @FXML
    private Label sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    private String characterImageName;

    AnimationTimerController backgroundSceneTimer;

    public EndController() {
        characterImageName = "miku.png";
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + characterImageName));
        characterImage.setFitHeight(ImagesConstant.imageHeight);
        characterImage.setFitWidth(ImagesConstant.imageWidth);
        characterImage.setImage(image);
    }

    private void handle() {
        backgroundSceneTimer = new AnimationTimerController(JavaFxConstant.sceneInterval) {
            @Override
            public void handle() {
                String imageName = ResourceRandomUtility.randomResources(ImagesConstant.quizImages);
                changeBackgroundImage(imageName);
            }
        };
        backgroundSceneTimer.start();
    }

    /**
     * Handle shortcut key inputs.
     * @param keyEvent key inputs.
     * @throws IOException key input error.
     */
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.H) {
            MusicController.playMusic("promise.wav");
            String imageName = ImagesConstant.startAppImages.get(ImagesEnum.START_APP_2);
            changeScene("home.fxml", imageName);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.R) {
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);
            changeScene("review.fxml", imageName);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.G) {
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);
            changeScene("girls.fxml", imageName);
            backgroundSceneTimer.stop();
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
}