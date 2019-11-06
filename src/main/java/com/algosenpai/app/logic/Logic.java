package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.ArchiveCommand;
import com.algosenpai.app.logic.command.BlockedCommand;
import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.ChaptersCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.HelpCommand;
import com.algosenpai.app.logic.command.HistoryCommand;
import com.algosenpai.app.logic.command.InvalidCommand;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.PrintArchiveCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.PrintQuizCommand;
import com.algosenpai.app.logic.command.PrintUserCommand;
import com.algosenpai.app.logic.command.QuizCommand;
import com.algosenpai.app.logic.command.QuizNextCommand;
import com.algosenpai.app.logic.command.QuizTestCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.ReviewCommand;
import com.algosenpai.app.logic.command.SaveCommand;
import com.algosenpai.app.logic.command.SelectCommand;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.logic.command.VolumeCommand;
import com.algosenpai.app.logic.constant.CommandsEnum;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Logic {
    private UserStats userStats;
    private QuizGenerator quizMaker = new QuizGenerator();

    //All variables for the quiz function
    private AtomicInteger chapterNumber = new AtomicInteger(-1);
    //Check if currently in quiz mode.
    private AtomicBoolean isQuizMode = new AtomicBoolean(false);
    //Checks if it is a new quiz.
    private AtomicBoolean isNewQuiz = new AtomicBoolean(true);
    //The arraylist containing the questions.
    private ArrayList<QuestionModel> quizList = new ArrayList<>();
    //The current question number.
    private AtomicInteger questionNumber = new AtomicInteger(0);
    private int prevResult = -1;

    // VariabReview features;
    private ArrayList<QuestionModel> archiveList = new ArrayList<>();

    //private HashSet<String> quizBlockedCommands = new HashSet<>(CommandsEnum.getNames());
    private HashSet<String> quizBlockedCommands = new HashSet<>(CommandsEnum.getBlockedNames(CommandsEnum.getNames()));
    // History features;
    private ArrayList<String> historyList = new ArrayList<>();
    // Used to get the past commands, using arrow keys. Stores the number of elements from the back of historyList
    // That the user wants to get. E.g. if user presses arrow UP, historyListOffset is incremented, DOWN -> decremented.
    private int historyListOffset = 0;

    /**
     * Initializes logic for the application with all the different components.
     */
    public Logic(UserStats stats) {
        this.userStats = stats;
    }

    /**
     * Executes the command.
     * @param input user input.
     * @return the command object to be executed.
     */
    public Command executeCommand(String input) {
        // reset the offset whenever the user executes a command
        historyListOffset = 0;
        ArrayList<String> inputs = Parser.parseInput(input);
        historyList.add(input);
        String userInput = inputs.get(0);
        //If the user quits, it has the highest priority and he can quit from any case.
        if (userInput.equals("bye")) {
            return new ByeCommand(inputs);
        }
        //Next priority is the quiz. When it is under quiz mode, no other commands can happen.
        if (isQuizMode.get()) {
            //If it is a new quiz
            if (isNewQuiz.get() && userInput.equals("quiz")) {
                return setupNewQuiz(inputs);
            } else if (isNewQuiz.get() && userInput.equals("select")) {
                return new SelectCommand(inputs, chapterNumber, userStats, isQuizMode);
            } else if ("menu".equals(userInput)) {
                return new MenuCommand(inputs);
            } else if ("history".equals(userInput)) {
                return new HistoryCommand(inputs, historyList);
            } else if ("volume".equals(userInput)) {
                return new VolumeCommand(inputs);
            } else if (quizBlockedCommands.contains(userInput)) {
                return new BlockedCommand(inputs);
            } else {
                return determineQuizAction(inputs);
            }
        } else {
            switch (userInput) {
            case "hello":
                return new SetupCommand(inputs, userStats);
            case "chapters":
                return new ChaptersCommand(inputs);
            case "help":
                return new HelpCommand(inputs, userStats);
            case "menu":
                return new MenuCommand(inputs);
            case "select":
                return new SelectCommand(inputs, chapterNumber, userStats, isQuizMode);
            case "result":
                return new ResultCommand(inputs, prevResult);
            case "history":
                return new HistoryCommand(inputs, historyList);
            case "undo":
                return new UndoCommand(inputs);
            case "clear":
                return new ClearCommand(inputs);
            case "reset":
                // TODO SHANTANU
                return null;
            case "save":
                // TODO SHANTANU
                return new SaveCommand(inputs, userStats);
            case "exit":
                return new ByeCommand(inputs);
            case "print":
                return getPrintCommand(inputs);
            case "archive":
                return new ArchiveCommand(inputs, quizList, archiveList);
            case "review":
                return new ReviewCommand(inputs, quizList);
            case "volume":
                return new VolumeCommand(inputs);
            default:
                return new InvalidCommand(inputs);
            }
        }
    }

    private Command determineQuizAction(ArrayList<String> inputs) {
        //if users do not enter anything.
        if (inputs.size() < 1) {
            return new QuizCommand(inputs);
        } else if (inputs.get(0).equals("next") || inputs.get(0).equals("back")) {
            return new QuizNextCommand(inputs, quizList, questionNumber);
        } else {
            return new QuizTestCommand(
                    inputs, quizList, questionNumber, isQuizMode, isNewQuiz, chapterNumber.get(),userStats);
        }
    }

    private Command setupNewQuiz(ArrayList<String> inputs) {
        quizList = quizMaker.generateQuiz(chapterNumber.get(), quizList);
        isNewQuiz.set(false);
        isQuizMode.set(true);
        return new QuizTestCommand(inputs, quizList, questionNumber, isQuizMode,
                isNewQuiz, chapterNumber.get(), userStats);
    }

    /**
     * Returns the type of print commands.
     * @param inputs user inputs.
     * @return type of print commands.
     */
    private Command getPrintCommand(ArrayList<String> inputs) {
        try {
            switch (inputs.get(1)) {
            case "user":
                return new PrintUserCommand(inputs, userStats);
            case "archive":
                return new PrintArchiveCommand(inputs, archiveList);
            case "quiz":
                return new PrintQuizCommand(inputs, quizList);
            default:
                return new PrintCommand(inputs);
            }
        } catch (IndexOutOfBoundsException e) {
            return new PrintCommand(inputs);
        }
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