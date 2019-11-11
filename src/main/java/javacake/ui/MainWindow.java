package javacake.ui;

import javacake.JavaCake;
import javacake.commands.EditNoteCommand;
import javacake.exceptions.CakeException;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionList;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.quiz.ReviewSession;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javacake.quiz.QuestionList.MAX_QUESTIONS;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends GridPane {
    @FXML
    private GridPane mainGrid;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
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

    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getPackageName());

    public static boolean isLightMode = true;
    public static boolean isChanged = false;
    public static boolean doneDialog = false;
    public static boolean isStupidUser = false;

    private JavaCake javaCake;
    private Stage primaryStage;

    private Image javaCakeImage = new Image(this.getClass().getResourceAsStream("/images/padoru.png"));

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
    public void initialize() {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.entering(getClass().getName(), "initialize");

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        taskScreen.vvalueProperty().bind(taskContainer.heightProperty());
        noteScreen.vvalueProperty().bind(noteContainer.heightProperty());
        avatarScreen.getChildren().add(AvatarScreen.setAvatar(AvatarScreen.AvatarMode.HAPPY));
        LOGGER.exiting(getClass().getName(), "initialize");
    }

    /**
     * Method to initialise all things javaCake related.
     * @param cake instance of JavaCake
     */
    public void setJavaCake(JavaCake cake) {
        javaCake = cake;
        TopBar top = new TopBar();
        top.setJavaCake(javaCake);
        top.setUpProgressBars();
        topBar.getChildren().add(top);

        try {
            if (javaCake.isFirstTimeUser) {
                response = Ui.showWelcomeMsgPhaseA(true);
                showContentContainer();
            } else {
                response = Ui.showWelcomeMsgPhaseA(false)
                        + Ui.showWelcomeMsgPhaseB(javaCake.isFirstTimeUser,
                        javaCake.userName, javaCake.storageManager);
                showContentContainer();
            }

            setAvatarDialogLoop();
            showListNotesBox();
            showRemindersBox();
            playGuiModeLoop();


            //Resize contentDialog to fit the current scrollpane
            playResizeLoop();
        } catch (NullPointerException | CakeException e) {
            System.out.println(e.getMessage());
            LOGGER.warning(e.getMessage());
        }

    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaCake's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     * If quiz is in session, no other commands are executed
     */
    @FXML
    private void handleUserInput() {
        LOGGER.entering(getClass().getName(), "handleUserInput");
        if (!isExit) {
            try {
                input = userInput.getText();
                // get input first, don't get response first...
                userInput.clear();
                LOGGER.info("userInput: " + input);
                DialogBox.isScrollingText = true;
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;
                String[] inputDivider = input.split("\\s+");
                if (inputDivider.length == 0) {
                    throw new CakeException("OOPS!!! I'm sorry, but I don't know what that means.");
                }
                if ("exit".equals(input)) {
                    //must be "exit" exactly, else wont exe
                    handleExit();
                } else if (isQuiz) {
                    handleOtherProcesses();
                } else if (isReview) {
                    LOGGER.info("Review Quiz Settings");
                    handleIsReview();
                } else if (isResult) { // On results screen
                    LOGGER.info("Display Results Settings");
                    handleIsResult();
                } else if (isTryingReset) { //confirmation of reset
                    LOGGER.info("Confirm Reset Settings");
                    handleResetConfirmation();
                } else if (isWritingNote) {
                    LOGGER.info("Writing Note Settings");
                    handleWriteNote();
                } else if ("listnote".equals(input)) {
                    //must be "listnote" exactly, else wont exe
                    handleListNote();
                } else if ("deletenote".equals(inputDivider[0])) {
                    handleDeleteNote();
                } else if ("createnote".equals(inputDivider[0])) {
                    handleCreateNote();
                } else if (isStarting && javaCake.isFirstTimeUser) { //set up new username
                    LOGGER.info("NEW USER Settings");
                    handleStartAndFirstTime();
                } else {
                    LOGGER.info("handleOtherProcesses { normal mode }");
                    handleOtherProcesses();
                }
            } catch (CakeException e) {
                response = e.getMessage();
                showContentContainer();
                LOGGER.warning(e.getMessage());
            }
        }
        LOGGER.exiting(getClass().getName(), "handleUserInput");
    }

    @FXML
    private void handleGuiMode() {
        LOGGER.entering(getClass().getName(), "handleGuiMode");
        if (isLightMode) { //switches to Dark theme
            isLightMode = false;
            mainGrid.setStyle("-fx-background-color: grey;");
            topBar.setStyle("-fx-background-color: #BBB; -fx-border-color: grey;");
            userInput.setStyle("-fx-background-color: #555; -fx-background-radius: 10;");
            dialogContainer.setStyle("-fx-background-color: grey;");
            avatarScreen.setStyle("-fx-background-color: black;");
            taskContainer.setStyle("-fx-background-color: grey;");
            noteContainer.setStyle("-fx-background-color: grey;");
            avatarDialog.setStyle("-fx-background-color: grey;");
            scrollPane.setStyle("-fx-background: grey;");
            taskScreen.setStyle("-fx-background: grey;");
            noteScreen.setStyle("-fx-background: grey;");
        } else { //switches to Light theme
            isLightMode = true;
            mainGrid.setStyle("-fx-background-color: pink;");
            topBar.setStyle("-fx-background-color: #EE8EC7; -fx-border-color: white;");
            userInput.setStyle("-fx-background-color: #EE8EC7;"
                    + " -fx-background-radius: 10;");
            dialogContainer.setStyle("-fx-background-color: pink;");
            avatarScreen.setStyle("-fx-background-color: black;");
            taskContainer.setStyle("-fx-background-color: pink;");
            noteContainer.setStyle("-fx-background-color: pink;");
            avatarDialog.setStyle("-fx-background-color: pink;");
            scrollPane.setStyle("-fx-background: pink;");
            taskScreen.setStyle("-fx-background: pink;");
            noteScreen.setStyle("-fx-background: pink;");
        }
        LOGGER.exiting(getClass().getName(), "handleGuiMode");
    }

    private void handleOtherProcesses() throws CakeException {
        LOGGER.entering(getClass().getName(), "handleOtherProcesses");

        response = javaCake.getResponse(input);
        if (isFirstQuiz()) {
            //do first quiz
            LOGGER.info("O1: isFirstQuiz");
        } else if (isQuiz) {
            handleQuiz();
            LOGGER.info("O2: handleQuiz");
        } else if (isDeadlineRelated()) {
            //handles "deadline" and "reminder" and other related
            LOGGER.info("O3: Deadline Settings");
        } else if (isColorRelated()) {
            //must be "change" exactly, else wont exe
            LOGGER.info("O4: Color Settings");
        } else if (isFirstResetRequest()) {
            //check if need to reset
            LOGGER.info("O5: isFirstResetRequest");
        } else if (!isQuiz || isStarting) {
            //default start: finding of response
            isStarting = false;
            LOGGER.info("O6: normal getResponse: " + response);
            //response = JavaCake.getResponse(input);
            if (response.contains("!@#_EDIT_NOTE")) {
                LOGGER.info("O6a: handleEditNote");
                handleEditNote();
            } else {
                LOGGER.info("O6b: handleNormalCommand");
                handleNormalCommand();
            }
        }
        LOGGER.exiting(getClass().getName(), "handleOtherProcesses");
    }

    private void handleIsResult() throws CakeException {
        LOGGER.info("handleIsResult");
        response = quizSession.parseInput(0, input);
        if ("!@#_REVIEW".equals(response)) {
            handleResultsScreenInput();
        } else if ("!@#_BACK".equals(response)) {
            handleBackCommand();
        }
    }

    private void handleIsReview() throws CakeException {
        LOGGER.info("handleIsReview");
        response = reviewSession.parseInput(0, input);
        if (isNumeric(response)) {
            handleGetReviewQuestion();
        } else if ("!@#_BACK".equals(response)) {
            handleBackCommand();
        }
    }

    private String initQuizSession(String cmdMode) throws CakeException {
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

    private void handleDeleteNote() throws CakeException {
        LOGGER.info("handleDeleteNote");
        response = javaCake.getResponse(input);
        showContentContainer();
        showListNotesBox();
    }

    private void handleNormalCommand() {
        LOGGER.info("NormalCommand Settings");
        System.out.println("starting BUT not firsttime");
        showContentContainer();
    }

    private void handleEditNote() throws CakeException {
        LOGGER.info("EditNote Settings");
        isWritingNote = true;
        response = EditNoteCommand.getHeadingMessage();
        //response.setEditable(false);
        DialogBox.isScrollingText = false;
        showContentContainer();
        EditNoteCommand.clearTextFileContent();
    }

    private void handleQuiz() throws CakeException {
        //Must be quizCommand: checking of answers
        LOGGER.info("Quiz Mode Settings");
        System.out.println("quiz answer checking");
        DialogBox.isScrollingText = false;
        handleGuiQuiz();
        showContentContainer();
    }

    private void handleWriteNote() throws CakeException {
        LOGGER.info("isWritingNote...");
        DialogBox.isScrollingText = false;
        if ("/save".equals(input)) {
            isWritingNote = false;
            response = EditNoteCommand.successSaveMessage();
        } else {
            response = EditNoteCommand.writeSaveGui(input);
        }
        showContentContainer();
    }

    private void handleListNote() throws CakeException {
        LOGGER.info("`listnote` command");
        showListNotesBox();
    }

    private void handleCreateNote() throws CakeException {
        LOGGER.info("`createnote` command");
        response = javaCake.getResponse(input);
        showContentContainer();
        showListNotesBox();
    }

    private void handleExit() {
        //System.out.println("EXIT");
        LOGGER.info("JavaCake has finished execution!");
        // find out if exit condition
        AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
        isExit = true;
        response = javaCake.getResponse(input);
        showContentContainer();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> primaryStage.hide());
        pause.play();
    }

    private void handleStartAndFirstTime() throws CakeException {
        System.out.println("start and first");
        LOGGER.info("New user initialising...");
        javaCake.userName = input;
        javaCake.storageManager.profile.overwriteName(javaCake.userName);
        response = Ui.showWelcomeMsgPhaseB(javaCake.isFirstTimeUser, javaCake.userName, javaCake.storageManager);
        showContentContainer();
        isStarting = false;
    }

    private void handleResetConfirmation() throws CakeException {
        LOGGER.info("isTryingReset...");
        System.out.println("resetting time");
        if ("yes".equals(input)) {
            //resets
            javaCake.storageManager.profile.resetProfile();
            javaCake.storageManager.storage.resetStorage();
            this.javaCake = new JavaCake();
            javaCake.storageManager.profile.writeColorConfig(isLightMode);
            javaCake.userProgress = javaCake.storageManager.profile.getTotalProgress();
            javaCake.userName = javaCake.storageManager.profile.getUsername();
            javaCake.isFirstTimeUser = true;
            showRemindersBox();
            showListNotesBox();
            response = "Reset confirmed!\nPlease type in new username:\n";
            TopBar.resetProgress();
            isStarting = true;
            LOGGER.info("Reset Confirmed!");
        } else {
            response = "Reset cancelled.\nType 'list' to get list of available commands.";
            LOGGER.info("Reset Rejected!");
        }
        showContentContainer();
        isTryingReset = false;
    }

    private void handleGuiQuiz() throws CakeException {
        LOGGER.info("Answering question " + index + "\nUser input: \"" + input + "\"");
        quizSession.parseInput(index, input);
        index++;
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
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog("You obviously can do better than that...\nbaka"));
            } else if (quizSession.scoreGrade == QuizSession.ScoreGrade.OKAY) {
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog("soooOOOOO CLOOSEEE!\nYou can do better next time!"));
            } else if (quizSession.scoreGrade == QuizSession.ScoreGrade.GOOD) {
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.EXTHAPPY;
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog("WELL DONE!!!\nYou rekt that cake!"));
            }
        }
    }

    private void handleResultsScreenInput() {
        isResult = false;
        isReview = true;
        reviewSession = new ReviewSession(tempQuestionList);
        response = reviewSession.getQuestion(0);
        LOGGER.info("Response: review session initialized");
        DialogBox.isScrollingText = false;
        showContentContainer();
    }

    private void handleBackCommand() {
        isResult = false;
        isReview = false;
        index = 0;
        response = javaCake.getResponse("back");
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
                DialogBox.getJavaCakeDialog(response, javaCakeImage));
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

    private void playResizeLoop() {
        AtomicReference<Double> prevDialogWidth = new AtomicReference<>(dialogContainer.getWidth());
        AtomicReference<Double> prevTaskWidth = new AtomicReference<>(taskContainer.getWidth());
        AtomicReference<Double> prevNoteWidth = new AtomicReference<>(noteContainer.getWidth());
        AtomicReference<Double> prevAvatarWidth = new AtomicReference<>(noteContainer.getWidth());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
            if (Math.abs(scrollPane.getWidth() - prevDialogWidth.get()) > 5) {
                dialogContainer.setPrefWidth(scrollPane.getWidth() - 15);
                prevDialogWidth.set(dialogContainer.getWidth());
            }
            if (Math.abs(taskScreen.getWidth() - prevTaskWidth.get()) > 5) {
                taskContainer.setPrefWidth(taskScreen.getWidth() - 20);
                prevTaskWidth.set(taskContainer.getWidth());
            }
            if (Math.abs(noteScreen.getWidth() - prevNoteWidth.get()) > 5) {
                noteContainer.setPrefWidth(noteScreen.getWidth() - 20);
                prevNoteWidth.set(noteContainer.getWidth());
            }
            if (Math.abs(noteScreen.getWidth() - prevAvatarWidth.get()) > 5) {
                avatarDialog.setPrefWidth(noteScreen.getWidth() - 15);
                prevAvatarWidth.set(noteContainer.getWidth());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private boolean isColorRelated() throws CakeException {
        if ("change".equals(input)) {
            LOGGER.info("ColorMode Settings");
            isChanged = true;
            LOGGER.info("is changing color!");
            javaCake.storageManager.profile.writeColorConfig(!isLightMode);
            return true;
        }
        return false;
    }

    private boolean isDeadlineRelated() throws CakeException {
        if (input.length() >= 8 && input.substring(0, 8).equals("deadline")) {
            if (!response.contains("[!]")) {
                deadlineExtracted();
                LOGGER.info("Adding deadlines setting");
            } else {
                response += "\nType 'reminder' to view deadlines";
                showTaskContainer();
                LOGGER.warning("Deadline is not properly parsed!");
            }
            return true;
        } else if (input.length() >= 4 && input.substring(0, 4).equals("done")) {
            if (!response.contains("[!]")) {
                deadlineExtracted();
                LOGGER.info("Done deadlines setting");
            } else {
                response += "\nType 'reminder' to view deadlines";
                showTaskContainer();
                LOGGER.warning("Deadline is not properly parsed!");
            }
            return true;
        } else if (input.length() >= 6 && input.substring(0, 6).equals("delete")) {
            if (!response.contains("[!]")) {
                deadlineExtracted();
                LOGGER.info("Removing deadlines setting");
            } else {
                response += "\nType 'reminder' to view deadlines";
                showTaskContainer();
                LOGGER.warning("Deadline is not properly parsed!");
            }
            return true;
        } else if (input.length() >= 6 && input.substring(0, 6).equals("snooze")) {
            if (!response.contains("[!]")) {
                deadlineExtracted();
                LOGGER.info("Changing deadlines setting");
            } else {
                response += "\nType 'reminder' to view deadlines";
                showTaskContainer();
                LOGGER.warning("Deadline is not properly parsed!");
            }
            return true;
        } else if ("reminder".equals(input)) {
            //must be "reminder" exactly, else wont exe
            showRemindersBox();
            LOGGER.info("Reminder setting");
            return true;
        }
        return false;
    }

    private void deadlineExtracted() {
        response = javaCake.getResponse("reminder");
        System.out.println(response);
        //CHECKSTYLE:OFF
        response = response.replaceAll("✓", "\u2713");
        response = response.replaceAll("✗", "\u2717");
        //CHECKSTYLE:ON
        showTaskContainer();
    }

    private boolean isFirstQuiz() throws CakeException {
        if (response.contains("!@#_QUIZ")) {
            LOGGER.info("First Quiz Settings");
            //checks for first execution of quizSession
            isQuiz = true;
            LOGGER.info("isFirstQuiz(): " + response);
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
            LOGGER.info("Reset Settings Waiting");
            isTryingReset = true;
            showContentContainer();
            return true;
        }
        return false;
    }

    private void showListNotesBox() throws CakeException {
        if (javaCake == null) {
            System.out.println("NO CAKE");
        }
        response = Ui.showNoteList(javaCake.storageManager);
        showNoteContainer();
    }

    private void showRemindersBox() throws CakeException {
        response = Ui.showDeadlineReminder(javaCake.storageManager);
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
        AtomicInteger bullyCount = new AtomicInteger();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), ev -> {
            if (isStupidUser) {
                avatarDialog.getChildren().clear();
                if (bullyCount.get() == 0) {
                    LOGGER.info("Cake is mad!");
                    avatarDialog.getChildren().add(
                            DialogBox.getTaskDialog("WHY ARE YOU TRYING TO CRASH MEE?!?!\n"
                                    + "baaAAKAAAAAAAAA"));
                    AvatarScreen.avatarMode = AvatarScreen.AvatarMode.POUT;
                } else if (bullyCount.get() == 1) {
                    LOGGER.info("Cake is sad!");
                    avatarDialog.getChildren().add(
                            DialogBox.getTaskDialog("FBI-kun!!!!\n"
                                    + "HAaAaaaalPP mmeeeeeeee"));
                    AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
                } else if (bullyCount.get() == 2) {
                    LOGGER.info("Cake is gaogao!");
                    avatarDialog.getChildren().add(
                            DialogBox.getTaskDialog("{JavaCake has been officially\n"
                                    + "                         "
                                    + "                       broken...}"));
                    AvatarScreen.avatarMode = AvatarScreen.AvatarMode.EXTHAPPY;
                }
                if (bullyCount.get() == 2) {
                    bullyCount.set(0);
                } else {
                    bullyCount.getAndIncrement();
                }
                isStupidUser = false;
                counterTicks.set(0);
            } else if (counterTicks.get() > 30 && !isExit && !isResult) {
                avatarDialog.getChildren().clear();
                avatarDialog.getChildren().add(
                        DialogBox.getTaskDialog(listToSay.get(rand.nextInt(listToSay.size()))));
                AvatarScreen.avatarMode = AvatarScreen.AvatarMode.HAPPY;
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
        list.add("Akshay-sensei is my favourite prof!!!");
        list.add("Learning Java\nis a piece of cake with JavaCake!! uWu");
        list.add("Learning Cake\nis a piece of java with CakeJava!! wUw");
        list.add("Hi, Welcome to JavaCake!\nWant sum cake?\nAll you have to do is get 100%!");
        list.add("I rather have Akshay than an A!\n");
        list.add("I LOVE BIG CAKES AND I CANNOT LIE!");
        list.add("CAAAAaaaaaaaaakkkke!");
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