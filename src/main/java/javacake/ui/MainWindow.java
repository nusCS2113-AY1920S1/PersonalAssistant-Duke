package javacake.ui;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.commands.QuizCommand;
import javacake.quiz.Question;
import javacake.storage.Profile;
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
    private Button sendButton = new Button();
    @FXML
    private HBox topBar;
    @FXML
    private VBox avatarScreen;
    @FXML
    private ScrollPane taskScreen;
    @FXML
    private VBox taskContainer;
    @FXML
    private ScrollPane noteScreen;
    @FXML
    private VBox noteContainer;
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
    private boolean isTryingReset = false;

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        taskScreen.vvalueProperty().bind(dialogContainer.heightProperty());
        noteScreen.vvalueProperty().bind(dialogContainer.heightProperty());
        avatarScreen.getChildren().add(AvatarScreen.setAvatar(AvatarScreen.AvatarMode.HAPPY));
        topBar.getChildren().add(new TopBar());
        TopBar.setUpProgressBars();

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
            String response = ""; // don't get response first...
            userInput.clear();
            Duke.logger.log(Level.INFO, input);
            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;
            if (input.contains("exit")) {
                // find out if exit condition
                response = duke.getResponse(input);
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> primaryStage.hide());
                pause.play();
            } else if (isStarting && duke.isFirstTimeUser) { //set up new username
                duke.userName = input;
                duke.profile.overwriteName(duke.userName);
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(Ui.showWelcomeMsgPhaseB(
                                duke.isFirstTimeUser, duke.userName, duke.userProgress), dukeImage)
                );
                isStarting = false;
            } else if (isTryingReset) { //confirmation of reset
                if (input.equals("yes")) {
                    //resets
                    Profile.resetProfile();
                    duke.profile = new Profile();
                    duke.userProgress = duke.profile.getTotalProgress();
                    duke.userName = duke.profile.getUsername();
                    duke.isFirstTimeUser = true;
                    response = "Reset confirmed!\nPlease type in new username:\n";
                    dialogContainer.getChildren().add(
                            DialogBox.getDukeDialog(response, dukeImage)
                    );
                    TopBar.resetProgress();
                    isStarting = true;
                } else {
                    response = "Reset cancelled.\nType 'list' to get list of available commands.";
                    dialogContainer.getChildren().add(
                            DialogBox.getDukeDialog(response, dukeImage)
                    );
                }
                isTryingReset = false;
            } else {
                if (isStarting) { //default start: finding of response
                    response = duke.getResponse(input);
                    isStarting = false;
                } else if (!isQuiz) { //default afterStart: finding of response
                    response = duke.getResponse(input);
                } else { //Must be quizCommand: checking of answers
                    quizCommand.checkAnswer(input);
                    if (quizCommand.chosenQuestions.size() > 0) {
                        response = quizCommand.getNextQuestion();
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

                if (response.contains("!@#_QUIZ")) { //checks if quizCommand was executed
                    isQuiz = true;
                    Duke.logger.log(Level.INFO, "Response: " + response);
                    //initalise the quiz here
                    response = getFirstQn(response);
                }
                if (response.contains("Confirm reset")) { //checks if resetCommand was executed
                    System.out.println("CHECKING RESET");
                    Duke.logger.log(Level.INFO, "Awaiting confirmation of reset");
                    isTryingReset = true;
                }

                //default output of response
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage));
                //System.out.println("End->Next");
            }
        } catch (DukeException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(e.getMessage(), dukeImage)
            );
        }
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
            avatarScreen.setStyle("-fx-background-color: grey;");
            taskContainer.setStyle("-fx-background-color: grey;");
            noteContainer.setStyle("-fx-background-color: grey;");
        } else { //switches to Light theme
            isLightMode = true;
            this.setStyle("-fx-background-color: white");
            sendButton.setStyle("-fx-background-color: #FF9EC7; -fx-border-color: white;");
            themeModeButton.setStyle("-fx-background-color: #FF9EC7; -fx-border-color: white;");
            topBar.setStyle("-fx-background-color: #EE8EC7; -fx-border-color: white;");
            userInput.setStyle("-fx-background-color: #EE8EC7;"
                    + " -fx-background-radius: 10;");
            dialogContainer.setStyle("-fx-background-color: pink;");
            avatarScreen.setStyle("-fx-background-color: #FEE;");
            taskContainer.setStyle("-fx-background-color: pink;");
            noteContainer.setStyle("-fx-background-color: pink;");
        }
    }


    private String getFirstQn(String cmdMode) throws DukeException {
        switch (cmdMode) {
        case "!@#_QUIZ_1":
            quizCommand = new QuizCommand(Question.QuestionType.BASIC, false);
            break;
        case "!@#_QUIZ_2":
            quizCommand = new QuizCommand(Question.QuestionType.OOP , false);
            break;
        case "!@#_QUIZ_3":
            quizCommand = new QuizCommand(Question.QuestionType.EXTENSIONS, false);
            break;
        case "!@#_QUIZ_4":
            quizCommand = new QuizCommand(Question.QuestionType.ALL, false);
            break;
        default:
        }
        return quizCommand.getNextQuestion();
    }
}