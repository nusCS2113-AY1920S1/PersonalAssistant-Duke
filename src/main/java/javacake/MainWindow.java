package javacake;

import javacake.commands.QuizCommand;
import javacake.quiz.Question;
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

    private Duke duke;
    private Stage primaryStage;


    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Hilda_Portrait.png"));



    private boolean isQuiz = false;
    private QuizCommand quizCommand;
    private boolean isStarting = true;


    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        rightScreen.getChildren().add(AvatarScreen.setAvatar(AvatarScreen.AvatarMode.HAPPY));
        topBar.getChildren().add(TopBar.setTitle());

        if (duke.isFirstTimeUser) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(Ui.showWelcomeMsgA(duke.isFirstTimeUser), dukeImage)
            );
        } else {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(
                            Ui.showWelcomeMsgA(duke.isFirstTimeUser)
                                    + Ui.showWelcomeMsgB(duke.isFirstTimeUser, duke.userName, duke.userProgress),
                            dukeImage)
            );
        }
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
            System.out.println(input);

            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;

            if (isStarting && duke.isFirstTimeUser) {
                duke.userName = input;
                duke.profile.overwriteName(duke.userName);
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(Ui.showWelcomeMsgB(
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
}