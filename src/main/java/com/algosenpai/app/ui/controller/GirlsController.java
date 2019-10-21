package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.CommandsConstant;
import com.algosenpai.app.logic.constant.JavaFxConstant;
import com.algosenpai.app.logic.constant.ImagesConstant;
import com.algosenpai.app.logic.constant.ViewConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.logic.constant.ResourcePathConstant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GirlsController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private StackPane container;

    @FXML
    private FlowPane options;

    private AnimationTimerController backgroundSceneTimer;

    /**
     * Initialize home scene.
     */
    public GirlsController() {
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Select Your Waifu!");
        setNodePos(sceneTitle, 100, -150);
        setTextStyle(sceneTitle, 199,21,133, true, 40, "arial");

        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayCharacterImage(characterImage, "miku.png", 300, 300);

        setNodePos(characterImage, 350.0, 0);

        displayCommandList(container, CommandsConstant.girlsCommand,
                255, 218, 185, true, 20, "arial",
                450.0, 300.0, 30);

        for (int i = 0; i < ImagesConstant.characterImagesList.size(); i++) {
            ImageView imageView = new ImageView();
            displayCharacterImage(imageView, ImagesConstant.characterImagesList.get(i), 150, 150);
            setNodePos(imageView, 150.0, -80.0 + i * 30);
            options.getChildren().add(imageView);
        }
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
        if (keyEvent.getCode() == KeyCode.D) {
            changeSceneOnKeyPressed(ViewConstant.dateView, ImagesConstant.dateImages, SoundConstant.dateSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            userInput.getParent().requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.M) {
            toggleVolume();
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (userInput.getText().equals("/select 1")) {
                Image image = new Image(getClass().getResourceAsStream(
                        ResourcePathConstant.imagesResourcePath + "miku.png"));
                characterImage.setImage(image);
            }
            if (userInput.getText().equals("/select 2")) {
                Image image = new Image(getClass().getResourceAsStream(
                        ResourcePathConstant.imagesResourcePath + "lolicon.png"));
                characterImage.setImage(image);
            }
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