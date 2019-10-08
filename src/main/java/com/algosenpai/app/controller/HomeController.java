package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.Chapter1;
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

public class HomeController extends SceneController implements Initializable {


    @FXML
    private ImageView characterImage;

    @FXML
    private Label sceneTitle;

    @FXML
    private Label sceneText;

    @FXML
    private TextField userInput;

    private String characterImageName;

    public HomeController() {
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
            changeScene(ResourcePathConstant.viewResourcePath + "quiz.fxml");
        }
        if (keyEvent.getCode() == KeyCode.R) {
            MusicController.playMusic("saturation.wav");
            changeScene(ResourcePathConstant.viewResourcePath + "review.fxml");
        }
        if (keyEvent.getCode() == KeyCode.G) {
            MusicController.playMusic("saturation.wav");
            changeScene(ResourcePathConstant.viewResourcePath + "girls.fxml");
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            userInput.getParent().requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.M) {
            toggleVolume();
        }
        //handling the user commands entered
        if (keyEvent.getCode() == KeyCode.ENTER) {
            switch (userInput.getText()) {
                case "menu":
                sceneText.setText("These are the commands available");
                break;
            case "start":
                sceneText.setText("The game is loading....");
                int questionNumber = 0;
                while (questionNumber < 10) {
                    Chapter1.generateQuestions();
                    questionNumber++;
                }
                break;
            case "exit":
                sceneText.setText("Aww you're leaving already? See you soon!");
                break;
            default:
                sceneText.setText("I'm sorry, I don't understand what you mean..");
            }
            userInput.setText("");
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