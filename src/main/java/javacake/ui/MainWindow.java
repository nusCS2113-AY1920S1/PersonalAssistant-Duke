package javacake.ui;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.commands.QuizCommand;
import javacake.quiz.Question;
import javacake.quiz.QuestionList;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.logging.Level;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private HBox topBar;
    @FXML
    private VBox rightScreen;
    @FXML
    private Button themeModeButton;
    public static boolean isLightMode = true;

    private Duke duke;
    private Stage primaryStage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/padoru.png"));

    private QuizCommand quizCommand;
    private boolean isQuiz = false;
    private boolean isStarting = true;

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        rightScreen.getChildren().add(AvatarScreen.setAvatar(AvatarScreen.AvatarMode.HAPPY));
        topBar.getChildren().add(TopBar.setTitle());
        setUpProgressBars();
        if (duke.isFirstTimeUser) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(Ui.showWelcomeMsgPhaseA(duke.isFirstTimeUser), dukeImage)
            );
        } else {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(
                            Ui.showWelcomeMsgPhaseA(duke.isFirstTimeUser)
                                    + Ui.showWelcomeMsgPhaseB(duke.isFirstTimeUser, duke.userName, duke.userProgress),
                            dukeImage)
            );
        }
    }

    private void setUpProgressBars() {
        TopBar.progValueA = (double) duke.profile.getContentMarks(0) / QuestionList.MAX_QUESTIONS;
        TopBar.progValueB = (double) duke.profile.getContentMarks(1) / QuestionList.MAX_QUESTIONS;
        TopBar.progValueC = (double) duke.profile.getContentMarks(2) / QuestionList.MAX_QUESTIONS;
        TopBar.progValueD = (double) duke.profile.getContentMarks(3) / QuestionList.MAX_QUESTIONS;
        TopBar.progValueT = (TopBar.progValueA
                + TopBar.progValueB
                + TopBar.progValueC
                + TopBar.progValueD) / 3;
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        try {
            dialogContainer.getChildren().clear();
            String input = userInput.getText();
            String response = "";
            userInput.clear();
            Duke.logger.log(Level.INFO, input);
            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;
            if (isStarting && duke.isFirstTimeUser) {
                duke.userName = input;
                duke.profile.overwriteName(duke.userName);
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(Ui.showWelcomeMsgPhaseB(
                                duke.isFirstTimeUser, duke.userName, duke.userProgress), dukeImage)
                );
                isStarting = false;
            } else if (isStarting) {
                response = duke.getResponse(input);
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                isStarting = false;
            } else {
                if (!isQuiz) {
                    response = duke.getResponse(input);
                } else {
                    quizCommand.checkAnswer(input);
                    if (quizCommand.chosenQuestions.size() > 0) {
                        response = quizCommand.getQuestion();
                    } else {
                        isQuiz = false;
                        response = quizCommand.getQuizScore();
                        if (quizCommand.scoreGrade == QuizCommand.ScoreGrade.BAD) {
                            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.POUT;
                        } else if (quizCommand.scoreGrade == QuizCommand.ScoreGrade.OKAY) {
                            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
                        }
                    }
                }
                if (response.contains("!@#_QUIZ")) {
                    isQuiz = true;
                    System.out.println(response);
                    response = getFirstQn(response);
                }
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
            }
            if (response.contains("Bye.")) {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> primaryStage.hide());
                pause.play();
            }
        } catch (DukeException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(e.getMessage(), dukeImage)
            );
        }
    }

    private String getFirstQn(String cmdMode) throws DukeException {
        switch (cmdMode) {
        case "!@#_QUIZ_1":
            quizCommand = new QuizCommand(Question.QuestionType.BASIC);
            break;
        case "!@#_QUIZ_2":
            quizCommand = new QuizCommand(Question.QuestionType.OOP);
            break;
        case "!@#_QUIZ_3":
            quizCommand = new QuizCommand(Question.QuestionType.EXTENSIONS);
            break;
        case "!@#_QUIZ_4":
            quizCommand = new QuizCommand();
            break;
        default:
        }
        return quizCommand.getQuestion();
    }

    @FXML
    private void handleGuiMode() {
        if (isLightMode) { //switches to Dark theme
            isLightMode = false;
            this.setStyle("-fx-background-color: black");
            sendButton.setStyle("-fx-background-color: #333; -fx-border-color: black;");
            themeModeButton.setStyle("-fx-background-color: #333; -fx-border-color: black;");
            topBar.setStyle("-fx-background-color: #BBB; -fx-border-color: grey;");
            userInput.setStyle("-fx-background-color: #9999; -fx-background-radius: 10;");
            dialogContainer.setStyle("-fx-background-color: grey;");
            rightScreen.setStyle("-fx-background-color: grey;");
        } else { //switches to Light theme
            isLightMode = true;
            this.setStyle("-fx-background-color: white");
            sendButton.setStyle("-fx-background-color: #FF9EC7; -fx-border-color: white;");
            themeModeButton.setStyle("-fx-background-color: #FF9EC7; -fx-border-color: white;");
            topBar.setStyle("-fx-background-color: #EE8EC7; -fx-border-color: white;");
            userInput.setStyle("-fx-background-color: #EE8EC7;"
                    + " -fx-background-radius: 10;");
            dialogContainer.setStyle("-fx-background-color: pink;");
            rightScreen.setStyle("-fx-background-color: #FEE;");
        }
    }
}