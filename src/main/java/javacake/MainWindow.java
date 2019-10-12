package javacake;

import javacake.commands.QuizCommand;
import javacake.quiz.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private boolean isQuiz = false;
    private QuizCommand quizCommand;
    private boolean isStarting = true;

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        if (duke.isFirstTimeUser) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(Ui.showWelcomeMsgA(duke.isFirstTimeUser), dukeImage)
            );
        } else {
            dialogContainer.getChildren().addAll(
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


    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        if (isStarting) {
            if (duke.isFirstTimeUser) {
                String input = userInput.getText();
                dialogContainer.getChildren().addAll(
                        DialogBox.getUserDialog(input, userImage),
                        DialogBox.getDukeDialog(Ui.showWelcomeMsgB(
                                duke.isFirstTimeUser, duke.userName, duke.userProgress), dukeImage)
                );
                duke.userName = input;
                duke.profile.overwriteName(duke.userName);
                userInput.clear();
            }
            isStarting = false;
        } else {
            String input = userInput.getText();
            System.out.println(input);
            String response = "";
            if (!isQuiz) {
                response = duke.getResponse(input);
            } else {
                quizCommand.checkAnswer(input);
                if (quizCommand.chosenQuestions.size() > 0) {
                    response = quizCommand.getQuestion();
                } else {
                    isQuiz = false;
                    response = quizCommand.getQuizScore();
                }
            }
            if (response.contains("!@#_QUIZ")) {
                isQuiz = true;
                response = getFirstQn(response);
            }
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );

            userInput.clear();
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