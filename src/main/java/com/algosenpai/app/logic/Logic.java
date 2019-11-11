package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.LectureGenerator;
import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.ChaptersCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.utility.HelpCommand;
import com.algosenpai.app.logic.command.QuizNextCommand;
import com.algosenpai.app.logic.command.critical.ArcadeCommand;
import com.algosenpai.app.logic.command.critical.ByeCommand;
import com.algosenpai.app.logic.command.critical.LectureCommand;
import com.algosenpai.app.logic.command.critical.QuizTestCommand;
import com.algosenpai.app.logic.command.critical.ResetCommand;
import com.algosenpai.app.logic.command.errorhandling.ArcadeBlockedCommand;
import com.algosenpai.app.logic.command.errorhandling.InvalidCommand;
import com.algosenpai.app.logic.command.errorhandling.LectureBlockedCommand;
import com.algosenpai.app.logic.command.errorhandling.QuizBlockedCommand;
import com.algosenpai.app.logic.command.utility.ArchiveCommand;
import com.algosenpai.app.logic.command.utility.ClearCommand;
import com.algosenpai.app.logic.command.utility.DeleteCommand;
import com.algosenpai.app.logic.command.utility.HistoryCommand;
import com.algosenpai.app.logic.command.utility.LoadCommand;
import com.algosenpai.app.logic.command.utility.MenuCommand;
import com.algosenpai.app.logic.command.utility.print.PrintCommandFactory;
import com.algosenpai.app.logic.command.utility.ResultCommand;
import com.algosenpai.app.logic.command.utility.ReviewCommand;
import com.algosenpai.app.logic.command.utility.SaveCommand;
import com.algosenpai.app.logic.command.utility.SelectLectureChapterCommand;
import com.algosenpai.app.logic.command.utility.SelectQuizChapterCommand;
import com.algosenpai.app.logic.command.utility.SetupCommand;
import com.algosenpai.app.logic.command.utility.ShowStatsCommand;
import com.algosenpai.app.logic.command.utility.VolumeCommand;
import com.algosenpai.app.logic.constant.CommandsEnum;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.LogCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Logic {

    private static final Logger logger = LogCenter.getLogger(Logic.class);

    /**
     * The different modes.
     */
    private AtomicBoolean isQuizMode = new AtomicBoolean(false);
    private AtomicBoolean isResetMode = new AtomicBoolean(false);
    private AtomicBoolean isLectureMode = new AtomicBoolean(false);
    private AtomicBoolean isArcadeMode = new AtomicBoolean(false);

    /**
     * Sets of commands blocked during respective modes.
     */
    private HashSet<String> quizBlockedCommands = new HashSet<>(CommandsEnum.getQuizBlockedNames());
    private HashSet<String> lectureBlockedCommands = new HashSet<>(CommandsEnum.getLectureBlockedNames());
    private  HashSet<String> arcadeBlockedCommands = new HashSet<>(CommandsEnum.getArcadeBlockedNames());

    /**
     * Variables related to the user.
     */
    private UserStats userStats;
    private AtomicInteger prevResults = new AtomicInteger(-1);
    private ArrayList<String> parsedUserInputs = new ArrayList<>();
    private String userCommand;

    /**
     * Quiz related variables.
     */
    private ArrayList<QuestionModel> quizList = new ArrayList<>();
    private AtomicInteger quizChapterNumber = new AtomicInteger(-1);
    private AtomicInteger quizQuestionNumber = new AtomicInteger(0);
    private AtomicBoolean isNewQuiz = new AtomicBoolean(true);

    /**
     * Lecture related variables.
     */
    private ArrayList<String> lectureSlides = new ArrayList<>();
    private AtomicInteger lectureChapterNumber = new AtomicInteger(-1);
    private AtomicInteger lectureSlideNumber = new AtomicInteger(0);
    private AtomicBoolean isNewLecture = new AtomicBoolean(true);

    /**
     * Utility variables.
     */
    private ArrayList<QuestionModel> archiveList = new ArrayList<>();
    private ArrayList<String> historyList = new ArrayList<>();
    private int historyListOffset = 0;

    /**
     * Initializes logic for the application with all the different components.
     */
    public Logic(UserStats stats) {
        this.userStats = stats;
    }

    /**
     * Executes the command corresponding to the user's input.
     * @param input The user's input.
     * @return The command executeed.
     */
    public Command executeCommand(String input) {
        addCommandHistory(input);
        parsedUserInputs = Parser.parseInput(input);
        userCommand = parsedUserInputs.get(0);

        if (userCommand.equals("exit")) {
            return executeBye();
        } else if (isResetMode.get() || userCommand.equals("reset")) {
            return executeReset();
        } else if (isQuizMode.get() || userCommand.equals("quiz")) {
            return executeQuiz();
        } else if (isLectureMode.get() || userCommand.equals("lecture")) {
            return executeLecture();
        } else if (isArcadeMode.get() || userCommand.equals("arcade")) {
            return executeArcade();
        } else {
            return executeOthers();
        }
    }

    private Command executeArcade() {
        if (arcadeBlockedCommands.contains(userCommand)) {
            return new ArcadeBlockedCommand(parsedUserInputs);
        } else {
            return new ArcadeCommand(parsedUserInputs, isArcadeMode);
        }
    }

    /**
     * Determines actions to be taken in normal mode.
     * @return The command given by the user, or invalid command is invalid.
     */
    private Command executeOthers() {
        switch (userCommand) {
        case "chapters":
            return new ChaptersCommand(parsedUserInputs);
        case "help":
            return new HelpCommand(parsedUserInputs, userStats);
        case "menu":
            return new MenuCommand(parsedUserInputs);
        case "result":
            return new ResultCommand(parsedUserInputs, prevResults);
        case "history":
            return new HistoryCommand(parsedUserInputs, historyList);
        case "delete":
            return new DeleteCommand(parsedUserInputs);
        case "clear":
            return new ClearCommand(parsedUserInputs);
        case "stats":
            return new ShowStatsCommand(parsedUserInputs, userStats);
        case "print":
            return new PrintCommandFactory(parsedUserInputs, userStats, archiveList, quizList);
        case "archive":
            return new ArchiveCommand(parsedUserInputs, quizList, archiveList);
        case "review":
            return new ReviewCommand(parsedUserInputs, quizList);
        case "volume":
            return new VolumeCommand(parsedUserInputs);
        case "hello":
            return new SetupCommand(parsedUserInputs, userStats);
        case "save":
            return new SaveCommand(parsedUserInputs, userStats);
        case "load":
            return new LoadCommand(parsedUserInputs, userStats);
        default:
            return new InvalidCommand(parsedUserInputs);
        }
    }

    /**
     * Determines actions to be taken in quiz mode.
     * @return The command given by the user.
     */
    private Command executeLecture() {
        if (isNewLecture.get() && userCommand.equals("start")) {
            return setupNewLecture();
        } else if (isNewLecture.get() && userCommand.equals("lecture")) {
            return new SelectLectureChapterCommand(parsedUserInputs, lectureChapterNumber, isLectureMode, userStats);
        } else if (userCommand.equals("volume")) {
            return new VolumeCommand(parsedUserInputs);
        } else if (lectureBlockedCommands.contains(userCommand) || isNewLecture.get()) {
            return new LectureBlockedCommand(parsedUserInputs);
        } else {
            return new LectureCommand(parsedUserInputs, lectureSlides, isLectureMode, lectureSlideNumber, isNewLecture);
        }
    }

    private Command setupNewLecture() {
        lectureSlides = new LectureGenerator().generateLecture(lectureChapterNumber.get());
        isNewLecture.set(false);
        isLectureMode.set(true);
        logger.info("Lecture mode has been initiated.");
        return new LectureCommand(parsedUserInputs, lectureSlides, isLectureMode,lectureSlideNumber, isNewLecture);
    }

    /**
     * Determines action to be taken for the quiz.
     * @return The command given.
     */
    private Command executeQuiz() {
        if (isNewQuiz.get() && userCommand.equals("start")) {
            return setupNewQuiz();
        } else if (isNewQuiz.get() && userCommand.equals("quiz")) {
            return new SelectQuizChapterCommand(parsedUserInputs, quizChapterNumber, userStats, isQuizMode);
        } else if (userCommand.equals("volume")) {
            return new VolumeCommand(parsedUserInputs);
        } else if (userCommand.equals("next") || userCommand.equals("back")) {
            return new QuizNextCommand(parsedUserInputs, quizList, quizQuestionNumber);
        } else if (quizBlockedCommands.contains(userCommand) || isNewQuiz.get()) {
            return new QuizBlockedCommand(parsedUserInputs);
        } else {
            return new QuizTestCommand(parsedUserInputs, quizList, quizQuestionNumber,
                    isQuizMode, isNewQuiz, quizChapterNumber.get(), userStats, prevResults);
        }
    }

    /**
     * Executes the reset Command.
     * @return The command reset.
     */
    private Command executeReset() {
        return new ResetCommand(parsedUserInputs, userStats, isResetMode);
    }

    /**
     * Executes the bye command.
     * @return The command bye.
     */
    private Command executeBye() {
        return new ByeCommand(parsedUserInputs);
    }

    /**
     * Sets up a new quiz.
     * @return The command for the quiz.
     */
    private Command setupNewQuiz() {
        logger.info("Quiz mode has been initiated");
        quizList = new QuizGenerator().generateQuiz(quizChapterNumber.get());
        isNewQuiz.set(false);
        isQuizMode.set(true);
        return new QuizTestCommand(parsedUserInputs, quizList, quizQuestionNumber, isQuizMode,
                isNewQuiz, quizChapterNumber.get(), userStats, prevResults);
    }

    /**
     * Appends the last command given by the user to a historylist.
     * @param input The user's input.
     */
    private void addCommandHistory(String input) {
        historyListOffset = 0;
        historyList.add(input);
    }

    /**
     * Get the previous command from historyList. Previous command is relative to the current value of offset.
     * i.e. you can call this function multiple times and it will return the history of commands entered in reverse
     * order. Similar to how terminal works.
     * @return The command entered.
     */
    public String getPreviousCommand() {
        // User has not typed in any commands yet.
        if (historyList.size() == 0) {
            return "";
        }

        historyListOffset++;
        if (historyListOffset > historyList.size()) {
            historyListOffset = historyList.size();
        }
        return historyList.get(historyList.size() - historyListOffset);
    }

    /**
     * Get the next command entered in history, relative to the current offset.
     * @return The command entered by the user, or empty string if the user has reached the present point in history.
     */
    public String getNextCommand() {
        historyListOffset--;
        if (historyListOffset <= 0) {
            historyListOffset = 0;
            // If 0, the user has returned to the current point in history. So return empty string, no more history.
            return "";
        }
        return historyList.get(historyList.size() - historyListOffset);
    }

}