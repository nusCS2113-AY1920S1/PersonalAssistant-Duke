package javacake.ui;

import javacake.Duke;
import javacake.commands.EditNoteCommand;
import javacake.exceptions.DukeException;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionList;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.quiz.ReviewSession;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import static javacake.quiz.QuestionList.MAX_QUESTIONS;

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
    private VBox avatarDialog;
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
    public static boolean isChanged = false;
    public static boolean doneDialog = false;

    private Duke duke;
    private Stage primaryStage;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/padoru.png"));

    private QuizSession quizSession;
    private ReviewSession reviewSession;
    private QuestionList tempQuestionList;
    private boolean isQuiz = false;
    private int index = 0;
    private boolean isResult = false;
    private boolean isReview = false;
    private boolean isStarting = true;
    private boolean isTryingReset = false;
    private boolean isWritingNote = false;
    private boolean isExit = false;
    private String input = "";
    private String response = "";

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() throws DukeException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        taskScreen.vvalueProperty().bind(taskContainer.heightProperty());
        noteScreen.vvalueProperty().bind(noteContainer.heightProperty());
        avatarScreen.getChildren().add(AvatarScreen.setAvatar(AvatarScreen.AvatarMode.HAPPY));
        topBar.getChildren().add(new TopBar());
        TopBar.setUpProgressBars();

        if (Duke.isFirstTimeUser) {
            response = Ui.showWelcomeMsgPhaseA(Duke.isFirstTimeUser);
            showContentContainer();
        } else {
            response = Ui.showWelcomeMsgPhaseA(Duke.isFirstTimeUser)
                    + Ui.showWelcomeMsgPhaseB(Duke.isFirstTimeUser, Duke.userName, Duke.userProgress);
            showContentContainer();
        }
        setAvatarDialogLoop();
        showListNotesBox();
        showRemindersBox();
        playGuiModeLoop();
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
        //CHECKSTYLE:OFF
        try {
            input = userInput.getText();
            String inputDivider[] = input.split("\\s+");
            // get input first, don't get response first...
            userInput.clear();
            Duke.logger.log(Level.INFO, "INPUT: " + input);
            DialogBox.isScrollingText = true;
            AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;
            if (input.equals("exit")) {
                handleExit();
            } else if (input.equals("listnote")) {
                handleListNote();
            } else if (inputDivider[0].equals("deletenote")) {
                handleDeleteNote();
            } else if (inputDivider[0].equals("createnote")) {
                handleCreateNote();
            } else if (isStarting && Duke.isFirstTimeUser) { //set up new username
                handleStartAndFirstTime();
            } else if (isTryingReset) { //confirmation of reset
                handleResetConfirmation();
            } else if (isWritingNote) {
                handleWriteNote();
            } else if (isResult) { // On results screen
                response = quizSession.parseInput(0, input);
                if (response.equals("!@#_REVIEW")) {
                    handleResultsScreenInput();
                } else if (response.equals("!@#_BACK")) {
                    handleBackCommand();
                }
            } else if (isReview) {
                response = reviewSession.parseInput(0, input);
                if (isNumeric(response)) {
                    handleGetReviewQuestion();
                } else if (response.equals("!@#_BACK")) {
                    handleBackCommand();
                }
            } else {
                Duke.logger.log(Level.INFO, "executing normal(else) mode!");
                response = duke.getResponse(input);
                if (isDeadlineRelated()) {
                    //handles "deadline" and "reminder"
                    Duke.logger.log(Level.INFO, "deadline setting");
                } else if (isFirstQuiz()) {
                    Duke.logger.log(Level.INFO, "First Quiz Incoming!");
                } else if (isFirstResetRequest()) {
                    Duke.logger.log(Level.INFO, "Reset command executed!");
                } else if (!isQuiz || isStarting) {
                    //default start: finding of response
                    isStarting = false;
                    Duke.logger.log(Level.INFO, "Response: " + response);
                    //response = duke.getResponse(input);
                    if (response.contains("!@#_EDIT_NOTE")) {
                        handleEditNote();
                    } else {
                        handleNormalCommand();
                    }
                } else if (isQuiz) {
                    handleQuiz();
                }
                //System.out.println("End->Next");
            }
        } catch (DukeException e) {
            response = e.getMessage();
            showContentContainer();
            Duke.logger.log(Level.WARNING, e.getMessage());
        }
        //CHECKSTYLE:ON
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
            avatarDialog.setStyle("-fx-background-color: grey;");
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
            avatarDialog.setStyle("-fx-background-color: #FEE;");
        }
    }


    private String initQuizSession(String cmdMode) throws DukeException {
        QuestionType qnType;
        QuestionDifficulty qnDifficulty;

        if (cmdMode.contains("!@#_QUIZ_1")) {
            qnType = QuestionType.BASIC;
        } else if (cmdMode.contains("!@#_QUIZ_2")) {
            qnType = QuestionType.OOP;
        } else if (cmdMode.contains("!@#_QUIZ_3")) {
            qnType = QuestionType.EXTENSIONS;
        } else {
            qnType = QuestionType.ALL;
        }

        if (cmdMode.contains("EZ")) {
            qnDifficulty = QuestionDifficulty.EASY;
        } else if (cmdMode.contains("MED")) {
            qnDifficulty = QuestionDifficulty.MEDIUM;
        } else {
            qnDifficulty = QuestionDifficulty.HARD;
        }
        quizSession = new QuizSession(qnType, qnDifficulty, false);

        return quizSession.getQuestion(0);
    }

    private void handleDeleteNote() throws DukeException {
        response = duke.getResponse(input);
        showContentContainer();
        showListNotesBox();
    }

    private void handleNormalCommand() {
        Duke.logger.log(Level.INFO, "Normal commands mode!");
        System.out.println("starting BUT not firsttime");
        showContentContainer();
    }

    private void handleEditNote() throws DukeException {
        Duke.logger.log(Level.INFO, "Editing note initialised!");
        isWritingNote = true;
        response = EditNoteCommand.getHeadingMessage();
        //response.setEditable(false);
        DialogBox.isScrollingText = false;
        /*dialogContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        //label.setVisible(false);
                        //TextArea textarea = new TextArea(label.getText());
                        //textarea.setPrefHeight(label.getHeight() + 10);
                        //stackpane.getChildren().add(textarea);

                        textarea.setOnKeyPressed(event ->{
                            System.out.println(event.getCode());
                            if(event.getCode().toString().equals("ENTER"))
                            {
                                stackpane.getChildren().remove(textarea);
                                label.setVisible(true);
                            }
                        });
                    }
            }
        });*/
        showContentContainer();
        EditNoteCommand.clearTextFileContent();
    }

    private void handleQuiz() throws DukeException {
        //Must be quizCommand: checking of answers
        Duke.logger.log(Level.INFO, "Quiz Mode!");
        System.out.println("quiz answer checking");
        DialogBox.isScrollingText = false;
        handleGuiQuiz();
        showContentContainer();
    }

    private void handleWriteNote() throws DukeException {
        Duke.logger.log(Level.INFO, "isWritingNote...");
        DialogBox.isScrollingText = false;
        if (input.equals("/save")) {
            isWritingNote = false;
            response = EditNoteCommand.successSaveMessage();
        } else {
            response = EditNoteCommand.writeSaveGui(input);
        }
        showContentContainer();
    }

    private void handleListNote() throws DukeException {
        Duke.logger.log(Level.INFO, "`listnote` command");
        showListNotesBox();
    }

    private void handleCreateNote() throws DukeException {
        Duke.logger.log(Level.INFO, "`createnote` command");
        response = duke.getResponse(input);
        showContentContainer();
        showListNotesBox();
    }

    private void handleExit() {
        System.out.println("EXIT");
        Duke.logger.log(Level.INFO, "EXITING PROGRAM!");
        // find out if exit condition
        isExit = true;
        response = duke.getResponse(input);
        showContentContainer();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> primaryStage.hide());
        pause.play();
    }

    private void handleStartAndFirstTime() throws DukeException {
        System.out.println("start and first");
        Duke.logger.log(Level.INFO, "New user initialising...");
        Duke.userName = input;
        Duke.storageManager.profile.overwriteName(Duke.userName);
        response = Ui.showWelcomeMsgPhaseB(Duke.isFirstTimeUser, Duke.userName, Duke.userProgress);
        showContentContainer();
        isStarting = false;
    }

    private void handleResetConfirmation() throws DukeException {
        Duke.logger.log(Level.INFO, "isTryingReset...");
        System.out.println("resetting time");
        if (input.equals("yes")) {
            //resets
            Profile.resetProfile();
            Storage.resetStorage();
            duke = new Duke();
            //            duke.profile = new Profile();
            Duke.userProgress = Duke.storageManager.profile.getTotalProgress();
            Duke.userName = Duke.storageManager.profile.getUsername();
            Duke.isFirstTimeUser = true;
            showRemindersBox();
            response = "Reset confirmed!\nPlease type in new username:\n";
            TopBar.resetProgress();
            isStarting = true;
            Duke.logger.log(Level.INFO, "Reset Confirmed!");
        } else {
            response = "Reset cancelled.\nType 'list' to get list of available commands.";
            Duke.logger.log(Level.INFO, "Reset Rejected!");
        }
        showContentContainer();
        isTryingReset = false;
    }

    private void handleGuiQuiz() throws DukeException {
        quizSession.parseInput(index++, input);
        if (index < MAX_QUESTIONS) {
            response = quizSession.getQuestion(index);
        } else {
            tempQuestionList = quizSession.getQuestionList();
            isQuiz = false;
            isResult = true;
            DialogBox.isScrollingText = true;
            response = quizSession.getQuizResult();
            doneDialog = true;
            if (quizSession.scoreGrade == QuizSession.ScoreGrade.BAD) {
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.POUT;
            } else if (quizSession.scoreGrade == QuizSession.ScoreGrade.OKAY) {
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
            }
        }
    }

    private void handleResultsScreenInput() {
        isResult = false;
        isReview = true;
        reviewSession = new ReviewSession(tempQuestionList);
        response = reviewSession.getQuestion(0);
        Duke.logger.log(Level.INFO, "Response: review session initialized");
        DialogBox.isScrollingText = false;
        showContentContainer();
    }

    private void handleBackCommand() {
        isResult = false;
        isReview = false;
        index = 0;
        response = duke.getResponse("back");
        showContentContainer();
    }

    private void handleGetReviewQuestion() {
        DialogBox.isScrollingText = false;
        response = reviewSession.getQuestion(Integer.parseInt(response));
        showContentContainer();
    }

    private void showContentContainer() {
        dialogContainer.getChildren().clear();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(response, dukeImage));
    }

    private void showTaskContainer() {
        taskContainer.getChildren().clear();
        taskContainer.getChildren().add(
                DialogBox.getTaskDialog(response));
    }

    private void showNoteContainer() {
        noteContainer.getChildren().clear();
        noteContainer.getChildren().add(
                DialogBox.getTaskDialog(response));
    }

    private void playGuiModeLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), ev -> {
            if (isLightMode && isChanged) { //change to dark mode
                handleGuiMode();
                isChanged = false;
            }
            if (!isLightMode && isChanged) { //change to light mode
                handleGuiMode();
                isChanged = false;
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private boolean isDeadlineRelated() {
        if (input.length() >= 8 && input.substring(0, 8).equals("deadline")) {
            //response = duke.getResponse(input);
            System.out.println(response);
            if (!response.contains("[!]")) {
                response = duke.getResponse("reminder");
                System.out.println(response);
                //CHECKSTYLE:OFF
                response = response.replaceAll("✓", "\u2713");
                response = response.replaceAll("✗", "\u2717");
                //CHECKSTYLE:ON
                showTaskContainer();
                Duke.logger.log(Level.INFO, "Adding deadlines setting");
            } else {
                response += "\nType 'reminder' to view deadlines";
                showTaskContainer();
                Duke.logger.log(Level.WARNING, "Deadline is not properly parsed!");
            }
            return true;
        } else if (input.equals("reminder")) {
            response = "Reminders are shown over there! ================>>>\n";
            showContentContainer();
            showRemindersBox();
            Duke.logger.log(Level.INFO, "Reminder setting");
            return true;
        }
        return false;
    }

    private boolean isFirstQuiz() throws DukeException {
        if (response.contains("!@#_QUIZ")) {
            //checks for first execution of quizCommand
            isQuiz = true;
            Duke.logger.log(Level.INFO, "isFirstQuiz(): " + response);
            response = initQuizSession(response);
            DialogBox.isScrollingText = false;
            showContentContainer();
            return true;
        }
        return false;
    }

    private boolean isFirstResetRequest() {
        if (response.contains("Confirm reset")) {
            //checks if resetCommand was executed
            Duke.logger.log(Level.INFO, "isFirstResetRequest(): Awaiting confirmation of reset");
            isTryingReset = true;
            showContentContainer();
            return true;
        }
        return false;
    }

    private void showListNotesBox() throws DukeException {
        response = Ui.showNoteList(Duke.storageManager);
        showNoteContainer();
    }

    private void showRemindersBox() {
        response = Ui.showDeadlineReminder(Duke.storageManager);
        //CHECKSTYLE:OFF
        response = response.replaceAll("✓", "\u2713");
        response = response.replaceAll("✗", "\u2717");
        //CHECKSTYLE:ON
        showTaskContainer();
    }

    private void setAvatarDialogLoop() {
        ArrayList<String> listToSay = new ArrayList<>();
        setList(listToSay);
        avatarDialog.getChildren().add(
                DialogBox.getTaskDialog(listToSay.get(0)));
        AtomicLong counterTicks = new AtomicLong();
        AtomicBoolean isSet = new AtomicBoolean();
        Random rand = new Random();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            if (counterTicks.get() > 30 && !isExit) {
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog(listToSay.get(rand.nextInt(listToSay.size()))));
                counterTicks.set(0);
            } else if (isExit && !isSet.get()) {
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog("NoooOOOOO!!\nDon't leeeeave meee\n:( :( :("));
                isSet.set(true);
            }
            counterTicks.getAndIncrement();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setList(ArrayList<String> list) {
        list.add("Hi, Welcome to JavaCake!\nWant sum cake?\nAll you have to do is get 100%!");
        //        list.add("WELL DONE!!!\nYou rekt that cake!\n");
        //        list.add("soooOOOOO CLOOSEEE!\nYou can do better next time!");
        //        list.add("Baaakaaa!\nYou obviously can do better than that...");
        list.add("I LOVE BIG CAKES AND I CANNOT LIE!");
        list.add("the cake...\n     is a LIE!");
        list.add("Your momma so fat...\nshe segfaulted on JavaCake");
        list.add("CAAAAAAAAAaaaaakkkke!");
        list.add("Want to know a secret?\nYour waifu does not love you!");
        list.add("I LOVE BIG CAKES\nAND I CANNOT LIE!");
        list.add("the cake...\n     is a LIE!");
        list.add("Your momma so fat...\nshe segfaulted on JavaCake");
        list.add("CAAAAAAAAAaaaaakkkke!");
        list.add("Want to know a secret?\nYour waifu does not love you!");
    }

    private static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}