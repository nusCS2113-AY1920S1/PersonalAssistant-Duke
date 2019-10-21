package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.CommandsConstant;
import com.algosenpai.app.logic.constant.JavaFxConstant;
import com.algosenpai.app.logic.constant.ImagesConstant;
import com.algosenpai.app.logic.constant.ViewConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
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

public class DateController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private Text sceneText;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private StackPane container;

    private AnimationTimerController backgroundSceneTimer;

    /**
     * Initialize quiz scene.
     */
    public DateController() {
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Date");
        setNodePos(sceneTitle, 50, 400);
        setTextStyle(sceneTitle, 199,21,133, true, 30, "arial");

        displayCharacterImage(characterImage, "miku.png", 400, 400);
        setNodePos(characterImage, 250.0, -350);

        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayDialogPane(dialogPane, 0.8, 2, 5, 255, 105, 180, 5);
        setNodePos(dialogPane, 450, -200);

        displayCommandList(container, CommandsConstant.dateCommand,
                255, 218, 185, true, 20, "arial",
                350.0, 250.0, 30);
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