package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class HomeController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private StackPane container ;

    AnimationTimerController backgroundSceneTimer;

    private List<Text> texts ;

    private List<String> commands ;

    public HomeController() {
        commands = new ArrayList<>();
        commands.add("/play");
        commands.add("/date");
        commands.add("/sound");
        commands.add("/exit");
        handle();
    }

    private int userInputY = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Welcome to AlgoSenpai Adventures!");
        setNodePos(sceneTitle, 0, 0);
        setTextStyle(sceneTitle, 199,21,133, true, 40, "arial");
        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + "miku.png"));
        characterImage.setImage(image);
        characterImage.setFitHeight(400);
        characterImage.setFitWidth(400);
        setNodePos(characterImage, 250.0, -200);
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
        backgroundSceneTimer = new AnimationTimerController(1) {
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
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.R) {
            MusicController.playMusic("saturation.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);
            changeScene("review.fxml", imageName);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.G) {
            MusicController.playMusic("saturation.wav");
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