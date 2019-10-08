package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
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

public class GirlsController extends SceneController implements Initializable {

    @FXML
    private Label sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    private String characterImageName;

    public GirlsController() {
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
        AnimationTimerController backgroundSceneTimer = new AnimationTimerController(JavaFxConstant.sceneInterval) {
            @Override
            public void handle() {
                String imageName = ResourceRandomUtility.randomResources(ImagesConstant.quizImages);
                changeBackgroundImage(ResourcePathConstant.imagesResourcePath + imageName);
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
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);

            changeScene("home.fxml", imageName);
            //changeScene(ResourcePathConstant.viewResourcePath + "home.fxml");
        }
        if (keyEvent.getCode() == KeyCode.D) {
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);

            changeScene("date.fxml", imageName);
            // changeScene(ResourcePathConstant.viewResourcePath + "date.fxml");
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