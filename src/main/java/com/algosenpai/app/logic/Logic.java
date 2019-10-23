//@@author carrieng0323852
package com.algosenpai.app.logic;

import com.algosenpai.app.logic.chapters.QuizGenerator;
import com.algosenpai.app.logic.command.BackCommand;
import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.HelpCommand;
import com.algosenpai.app.logic.command.HistoryCommand;
import com.algosenpai.app.logic.command.InvalidCommand;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.QuizCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.stats.UserStats;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class Logic {

    private Parser parser;
    private UserStats userStats;
    private QuizGenerator quizMaker;

    //All variables for the settings of the program
    private int setupStage = 0;
    private AtomicBoolean isSettingUp = new AtomicBoolean(false);
    private double playerExp = 0.0;

    //All variables for the quiz function
    private int selectedChapters = 0;
    private AtomicBoolean isQuizMode = new AtomicBoolean(false);
    private AtomicBoolean isNewQuiz = new AtomicBoolean(true);
    private ArrayList<QuestionModel> quizList;
    private AtomicInteger questionNumber = new AtomicInteger(0);
    private int prevResult = 0;

    // Review features;
    private ArrayList<QuestionModel> reviewList;

    // History features;
    private ArrayList<String> historyList;

    /**
     * Initializes logic for the application.
     * @param parser parser for user inputs.
     * @param userStats user states.
     */
    public Logic(Parser parser, UserStats userStats) {
        this.parser = parser;
        this.userStats = userStats;
        quizMaker = new QuizGenerator();
        historyList = new ArrayList<>();
    }

    /**
     * Executes the command.
     * @param input user input.
     * @return the command object to be executed.
     */
    public Command executeCommand(String input) {
        ArrayList<String> inputs = Parser.parseInput(input);

        if (isQuizMode.get()) {
            if (inputs.get(0).equals("back")) {
                if (isQuizMode.get()) {
                    return new BackCommand(inputs, quizList, questionNumber);
                }
            }
            return new QuizCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
        }

        if (isSettingUp.get()) {
            setupStage++;
            if (setupStage == 3) {
                isSettingUp.set(false);
            }
            return new SetupCommand(inputs, setupStage, isSettingUp);
        }

        switch (inputs.get(0)) {
        case "hello":
            if (!isSettingUp.get()) {
                isSettingUp.set(true);
                setupStage++;
                return new SetupCommand(inputs, setupStage, isSettingUp);
            }
            return new SetupCommand(inputs, setupStage, isSettingUp);
        case "help":
            return new HelpCommand(inputs);
        case "menu":
            return new MenuCommand(inputs);
        case "select":
            // TODO
            return null;
        case "result":
            return new ResultCommand(inputs, prevResult);
        case "report":
            return new PrintCommand(inputs, userStats);
        case "history":
            return new HistoryCommand(inputs, historyList);
        case "undo":
            return new UndoCommand(inputs);
        case "clear":
            return new ClearCommand(inputs);
        case "reset":
            // TODO
            return null;
        case "save":
            // TODO
            return null;
        case "exit":
            return new ByeCommand(inputs);
        case "print":
            return new PrintCommand(inputs, quizList);
        case "archive":
            // TODO
            return null;
        case "quiz":
            if (isNewQuiz.get()) {
                quizList = quizMaker.generateQuiz(selectedChapters, quizList);
                isNewQuiz.set(false);
                isQuizMode.set(true);
            }
            return new QuizCommand(inputs, quizList, questionNumber, isQuizMode, isNewQuiz);
        case "back":
            if (isQuizMode.get()) {
                return new BackCommand(inputs, quizList, questionNumber);
            }
            return new InvalidCommand(inputs);
        default:
            return new InvalidCommand(inputs);
        }
    }
}
