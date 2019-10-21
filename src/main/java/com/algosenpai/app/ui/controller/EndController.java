package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.CommandsConstant;
import com.algosenpai.app.logic.constant.JavaFxConstant;
import com.algosenpai.app.logic.constant.ImagesConstant;
import com.algosenpai.app.logic.constant.ViewConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
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

public class EndController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private Text subSceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private StackPane container;

    private AnimationTimerController backgroundSceneTimer;

    /**
     * Initialize home scene.
     */
    public EndController() {
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("End Of Quiz");
        setNodePos(sceneTitle, 150, -100);
        setTextStyle(sceneTitle, 255,218,185, true, 40, "arial");

        subSceneTitle.setText("Score: 0 / 10");
        setNodePos(subSceneTitle, 200, -80);
        setTextStyle(subSceneTitle, 255,218,185, true, 30, "arial");

        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayCharacterImage(characterImage, "miku.png", 400, 400);
        setNodePos(characterImage, 250.0, 0);

        displayCommandList(container, CommandsConstant.endCommand,
                255, 218, 185, true, 20, "arial",
                300.0, 250.0, 30);
    }

    private void handle() {
        backgroundSceneTimer = new AnimationTimerController(JavaFxConstant.sceneInterval) {
            @Override
            public void handle() {

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
            changeSceneOnKeyPressed(ViewConstant.homeView, ImagesConstant.homeImages, SoundConstant.homeSound);
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