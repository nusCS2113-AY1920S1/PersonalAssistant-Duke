package com.algosenpai.app.controller;

import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.JavaFxConstant;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    private List<Text> texts;

    private List<String> commands;

    private List<String> waifus;

    public GirlsController() {
        commands = new ArrayList<>();
        commands.add("/home");
        commands.add("/select");
        commands.add("/fun");
        commands.add("/exit");
        waifus = new ArrayList<>();
        waifus.add("miku.png");
        waifus.add("lolicon.png");
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Select Your Waifu!");
        setNodePos(sceneTitle, 100, -150);
        setTextStyle(sceneTitle, 199,21,133, true, 40, "arial");
        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + "miku.png"));
        characterImage.setImage(image);
        characterImage.setFitHeight(300);
        characterImage.setFitWidth(300);
        setNodePos(characterImage, 350.0, 0);
        texts = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            Text text = new Text(commands.get(i));
            setTextStyle(text, 255,218,185, true, 20, "arial");
            setNodePos(text, 450.0 + i * 30, 300.0);
            texts.add(text);
            container.getChildren().add(text);
        }
        for (int i = 0; i < waifus.size(); i++) {
            Image img = new Image(getClass().getResourceAsStream(
                    ResourcePathConstant.imagesResourcePath + waifus.get(i)));
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(150.0);
            imageView.setFitWidth(150.0);
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
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);
            changeScene("home.fxml", imageName);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.D) {
            MusicController.playMusic("romeo-and-cinderella.wav");
            String imageName = "bedroom.jpg";
            changeScene("date.fxml", imageName);
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