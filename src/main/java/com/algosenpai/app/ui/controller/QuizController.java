package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.Question;
import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.constant.CommandsConstant;
import com.algosenpai.app.logic.constant.JavaFxConstant;
import com.algosenpai.app.logic.constant.ImagesConstant;
import com.algosenpai.app.logic.constant.ViewConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;

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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizController extends SceneController implements Initializable {

    private String userAnswer;

    private int questionNumber = 0;

    private int correctCount = 0;

    private QuizGenerator generator = new QuizGenerator();

    private Question currQuestion;

    private ArrayList<Question> questionList;


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
    public QuizController() {
        handle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneTitle.setText("Quiz");
        setNodePos(sceneTitle, 50, 400);
        setTextStyle(sceneTitle, 199,21,133, true, 30, "arial");

        //generate the question list for the quiz and display the first question
        generator.generateQuiz(1, this.questionList);
        currQuestion = questionList.get(questionNumber);
        sceneText.setText("hello");
        setNodePos(sceneText, 360, 55);
        setTextStyle(sceneText, 255,255,255, true, 9, "arial");

        displayCharacterImage(characterImage, "miku.png", 400, 400);
        setNodePos(characterImage, 250.0, -350);

        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayDialogPane(dialogPane, 0.8, 2, 5, 255, 105, 180, 5);
        setNodePos(dialogPane, 450, -200);

        displayCommandList(container, CommandsConstant.quizCommand,
                255, 218, 185, true, 20, "arial",
                350.0, 250.0, 30);

        //handle any answers entered
        userInput.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                userAnswer = userInput.getText();
                //parser.parse(userAnswer);
                try {
                    handleAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userInput.setText("");
                sceneText.setText(currQuestion.getQuestion());
            }
        });
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
            changeSceneOnKeyPressed(ViewConstant.homeView, ImagesConstant.homeImages, SoundConstant.homeSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.E) {
            changeSceneOnKeyPressed(ViewConstant.endView, ImagesConstant.endImages, SoundConstant.endSound);
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

    /**
     * Handle the answer input by the user.
     */
    public void handleAnswer() throws IOException {
        if ((questionNumber > 9) || (userAnswer.equals("end"))) {
            changeSceneOnKeyPressed(ViewConstant.endView, ImagesConstant.endImages, SoundConstant.endSound);
            backgroundSceneTimer.stop();
        }
        if (userAnswer.equals(currQuestion.getAnswer())) {
            correctCount++;
        }
        if (userAnswer.equals("back")) {
            questionNumber += 1;
        }
        questionNumber++;
        currQuestion = (Question) questionList.get(questionNumber);
    }

}