package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.ArchiveCommand;
import com.algosenpai.app.logic.command.VolumeCommand;
import com.algosenpai.app.logic.command.ByeCommand;
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
import com.algosenpai.app.logic.command.QuizNextCommand;
import com.algosenpai.app.logic.command.QuizTestCommand;
import com.algosenpai.app.logic.command.QuizCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.ReviewCommand;
import com.algosenpai.app.logic.command.SaveCommand;
import com.algosenpai.app.logic.command.SelectCommand;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;
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

    // History features;
    private ArrayList<String> historyList = new ArrayList<>();

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
            } else {
                return determineQuizAction(inputs);
            }
        } else {
            switch (userInput) {
            case "hello":
                return new SetupCommand(inputs, userStats);
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
}