package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.ArchiveCommand;
import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.HelpCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.HistoryCommand;
import com.algosenpai.app.logic.command.InvalidCommand;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.PrintArchiveCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.PrintQuizCommand;
import com.algosenpai.app.logic.command.PrintUserCommand;
import com.algosenpai.app.logic.command.QuizNextCommand;
import com.algosenpai.app.logic.command.QuizTestCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.SaveCommand;
import com.algosenpai.app.logic.command.SelectCommand;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class Logic {
    private Parser parser;
    private UserStats userStats;
    private QuizGenerator quizMaker;

    //All variables for the settings of the program
    private double playerExp = 0.0;

    //All variables for the quiz function
    private AtomicInteger chapterNumber = new AtomicInteger(-1);
    private AtomicBoolean isQuizMode = new AtomicBoolean(false);
    private AtomicBoolean isNewQuiz = new AtomicBoolean(true);
    private ArrayList<QuestionModel> quizList;
    private AtomicInteger questionNumber = new AtomicInteger(0);
    private int prevResult = -1;

    // VariabReview features;
    private ArrayList<QuestionModel> archiveList;

    // History features;
    private ArrayList<String> historyList;

    /**
     * Initializes logic for the application with all the different components.
     */
    public Logic(UserStats stats) throws FileNotFoundException {
        this.parser = new Parser();
        this.userStats = stats;
        quizMaker = new QuizGenerator();
        historyList = new ArrayList<>();
        archiveList = new ArrayList<>();
    }

    /**
     * Gets the player exp level.
     * @return the double value representing the exp level.
     */
    public double getPlayerExp() {
        return this.playerExp;
    }

    /**
     * Executes the command.
     * @param input user input.
     * @return the command object to be executed.
     */
    public Command executeCommand(String input) {
        ArrayList<String> inputs = Parser.parseInput(input);
        historyList.add(input);

        if (isQuizMode.get() || inputs.get(0).equals("quiz")) {
            return getQuizCommand(inputs);
        }

        switch (inputs.get(0)) {
        case "hello":
            return new SetupCommand(inputs, userStats);
        case "help":
            return new HelpCommand(inputs);
        case "menu":
            return new MenuCommand(inputs);
        case "select":
            return new SelectCommand(inputs, chapterNumber, userStats);
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
        default:
            return new InvalidCommand(inputs);
        }
    }

    private Command getQuizCommand(ArrayList<String> inputs) {
        if (!isNewQuiz.get()) {
            if (inputs.get(1).equals("next") || inputs.get(1).equals("back")) {
                return new QuizNextCommand(inputs, quizList, questionNumber);
            } else {
                return new QuizTestCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
            }
        }
        quizList = quizMaker.generateQuiz(chapterNumber.get(), quizList);
        return new QuizTestCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
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
