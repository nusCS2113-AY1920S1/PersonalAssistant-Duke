package javacake.ui;

import javacake.Duke;
import javacake.Main;
import javacake.commands.QuizCommand;
import javacake.quiz.QuestionList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class TopBar extends HBox {
    @FXML
    private ImageView cakeLeft;
    @FXML
    private Label title;
    @FXML
    private Label quizA;
    @FXML
    private Label quizB;
    @FXML
    private Label quizC;
    @FXML
    private Label quizD;
    @FXML
    private ImageView cakeRight;
    @FXML
    private ProgressBar progressA = new ProgressBar();
    public static double progValueA = 0;
    @FXML
    private ProgressBar progressB = new ProgressBar();
    public static double progValueB = 0;
    @FXML
    private ProgressBar progressC = new ProgressBar();
    public static double progValueC = 0;
    @FXML
    private ProgressBar progressD = new ProgressBar();
    public static double progValueD = 0;
    @FXML
    private ProgressIndicator progressTotal = new ProgressIndicator();
    public static double progValueT = 0;


    private Image cakeTiltLeft = new Image(this.getClass().getResourceAsStream("/images/cake_left.png"));
    private Image cakeTiltRight = new Image(this.getClass().getResourceAsStream("/images/cake_right.png"));
    private Image cakeOriginal = new Image(this.getClass().getResourceAsStream("/images/cake.png"));
    private int cakeCounter = 0;
    private boolean isSet = false;

    /**
     * Constructor for title bar.
     */
    public TopBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/TopBar.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAnimation();
        setStyleLoop();
    }

    /**
     * Method to set TopBar.
     * @return TopBar object
     */
    public static TopBar setTitle() {
        return new TopBar();
    }

    private void setStyleLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), ev -> {
            if (MainWindow.isLightMode) {
                if (!isSet) {
                    progressTotal.getStylesheets().remove(
                            getClass().getResource("/css/progDark.css").toExternalForm());
                    title.setStyle("-fx-text-fill: white");
                    quizA.setStyle("-fx-text-fill: white");
                    quizB.setStyle("-fx-text-fill: white");
                    quizC.setStyle("-fx-text-fill: white");
                    quizD.setStyle("-fx-text-fill: white");
                    isSet = true;
                }
            } else {
                if (isSet) {
                    progressTotal.getStylesheets().add(
                            getClass().getResource("/css/progDark.css").toExternalForm());
                    title.setStyle("-fx-text-fill: black");
                    quizA.setStyle("-fx-text-fill: black");
                    quizB.setStyle("-fx-text-fill: black");
                    quizC.setStyle("-fx-text-fill: black");
                    quizD.setStyle("-fx-text-fill: black");
                    isSet = false;
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), ev -> {
            if (cakeCounter % 4 == 0) {
                cakeLeft.setImage(cakeTiltLeft);
                cakeRight.setImage(cakeTiltRight);
            } else if (cakeCounter % 4 == 1) {
                cakeLeft.setImage(cakeOriginal);
                cakeRight.setImage(cakeOriginal);
            } else if (cakeCounter % 4 == 2) {
                cakeLeft.setImage(cakeTiltRight);
                cakeRight.setImage(cakeTiltLeft);
            } else {
                cakeLeft.setImage(cakeOriginal);
                cakeRight.setImage(cakeOriginal);
            }
            progressA.setProgress(progValueA);
            progressB.setProgress(progValueB);
            progressC.setProgress(progValueC);
            progressD.setProgress(progValueD);
            progressTotal.setProgress(progValueT);
            cakeCounter++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method to reset progress.
     */
    public static void resetProgress() {
        progValueA = 0;
        progValueB = 0;
        progValueC = 0;
        progValueD = 0;
        progValueT = 0;
    }

    /**
     * Method to set up progressBars.
     */
    public static void setUpProgressBars() {
        TopBar.progValueA = (double) Duke.profile.getContentMarks(0) / QuizCommand.MAX_QUESTIONS;
        TopBar.progValueB = (double) Duke.profile.getContentMarks(1) / QuizCommand.MAX_QUESTIONS;
        TopBar.progValueC = (double) Duke.profile.getContentMarks(2) / QuizCommand.MAX_QUESTIONS;
        TopBar.progValueD = (double) Duke.profile.getContentMarks(3) / QuizCommand.MAX_QUESTIONS;
        TopBar.progValueT = (double) Duke.profile.getTotalProgress() / (QuizCommand.MAX_QUESTIONS * 4);
    }

}
