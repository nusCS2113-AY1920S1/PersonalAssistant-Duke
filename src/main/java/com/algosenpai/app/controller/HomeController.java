package com.algosenpai.app.controller;

import com.algosenpai.app.constant.CommandsConstant;
import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.ViewConstant;
import com.algosenpai.app.constant.SoundConstant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private StackPane container;

    private AnimationTimerController backgroundSceneTimer;

    private int userInputY = 0;

    /**
     * Initialize home scene.
     */
    public HomeController() {
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Welcome to AlgoSenpai Adventures!");
        setNodePos(sceneTitle, 0, 0);
        setTextStyle(sceneTitle, 199,21,133, true, 40, "arial");

        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayCharacterImage(characterImage, "miku.png", 400, 400);
        setNodePos(characterImage, 250.0, -200);

        displayCommandList(container, CommandsConstant.homeCommand,
                255, 218, 185, true, 20, "arial",
                300.0, 250.0, 30);
    }

    private void handle() {
        backgroundSceneTimer = new AnimationTimerController(1) {
            @Override
            public void handle() {
                if (userInputY <= 250) {
                    userInputY += 2;
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
            changeSceneOnKeyPressed(ViewConstant.quizView, ImagesConstant.quizImages, SoundConstant.quizSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.R) {
            changeSceneOnKeyPressed(ViewConstant.reviewView, ImagesConstant.reviewImages, SoundConstant.reviewSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.G) {
            changeSceneOnKeyPressed(ViewConstant.girlsView, ImagesConstant.girlsImages, SoundConstant.girlsSound);
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