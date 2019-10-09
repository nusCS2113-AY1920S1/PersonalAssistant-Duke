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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    private List<Text> texts;

    private List<String> commands;

    public EndController() {
        commands = new ArrayList<>();
        commands.add("/home");
        commands.add("/date");
        commands.add("/review");
        commands.add("/exit");
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
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + "miku.png"));
        characterImage.setImage(image);
        characterImage.setFitHeight(400);
        characterImage.setFitWidth(400);
        setNodePos(characterImage, 250.0, 0);
        texts = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            Text text = new Text(commands.get(i));
            setTextStyle(text, 255,218,185, true, 20, "arial");
            setNodePos(text, 300.0 + i * 30, 250.0);
            texts.add(text);
            container.getChildren().add(text);
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
            MusicController.playMusic("asayake-no-starmine.wav");
            String imageName = "kiss.png";
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