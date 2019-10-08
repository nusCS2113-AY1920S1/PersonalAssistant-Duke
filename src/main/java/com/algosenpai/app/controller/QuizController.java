package com.algosenpai.app.controller;

import com.algosenpai.app.Question;
import com.algosenpai.app.chapters.ChapterSorting;
import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.ImagesEnum;
import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController extends SceneController implements Initializable {

    private Question newQuestion;

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

    private List<Text> texts;

    private List<String> commands;

    /**
     * Initialize quiz scene.
     */
    public QuizController() {
        commands = new ArrayList<>();
        commands.add("/home");
        commands.add("/end");
        commands.add("/next");
        commands.add("/prev");
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Quiz");
        setNodePos(sceneTitle, 50, 400);
        setTextStyle(sceneTitle, 199,21,133, true, 30, "arial");
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + "miku.png"));
        characterImage.setImage(image);
        characterImage.setFitHeight(400);
        characterImage.setFitWidth(400);
        setNodePos(characterImage, 250.0, -350);
        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);
        dialogPane.setOpacity(0.8);
        dialogPane.setScaleX(2);
        dialogPane.setScaleY(5);
        dialogPane.setBackground(new Background(
                new BackgroundFill(Color.rgb(255,105,180), new CornerRadii(5), Insets.EMPTY)));
        setNodePos(dialogPane, 450, -200);
        texts = new ArrayList<>();
        for (int i = 0; i < commands.size(); i++) {
            Text text = new Text(commands.get(i));
            setTextStyle(text, 255,218,185, true, 20, "arial");
            setNodePos(text, 350.0 + i * 30, 250.0);
            texts.add(text);
            container.getChildren().add(text);
        }
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
        if (keyEvent.getCode() == KeyCode.E) {
            MusicController.playMusic("rezero.wav");
            String imageName = ResourceRandomUtility.randomResources(ImagesConstant.startAppImages);
            changeScene("end.fxml", imageName);
            backgroundSceneTimer.stop();
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
                    newQuestion = ChapterSorting.generateQuestions();
                    assert newQuestion != null;
                    sceneText.setText(newQuestion.getQuestion());
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        if (newQuestion.isAnswerEqual(userInput.getText())) {
                            sceneText.setText("Correct!");
                        } else {
                            sceneText.setText(newQuestion.getAnswer());
                        }
                    }
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