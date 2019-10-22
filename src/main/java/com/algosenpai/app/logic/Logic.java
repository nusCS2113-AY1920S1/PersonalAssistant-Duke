package com.algosenpai.app.logic;

import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.MenuCommand;
import com.algosenpai.app.logic.command.PrintCommand;
import com.algosenpai.app.logic.command.ResultCommand;
import com.algosenpai.app.logic.command.HistoryCommand;
import com.algosenpai.app.logic.command.QuizCommand;
import com.algosenpai.app.logic.command.InvalidCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.BackCommand;
import com.algosenpai.app.logic.command.CommandEnum;
import com.algosenpai.app.logic.chapters.QuizGenerator;
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
    private boolean isNew = true;
    private boolean isSettingUp = false;
    private int level = 0;
    private String name;

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
     * Parses the user input for the command to be created and executed.
     * @param userString the user input from the GUI.
     * @return the Command object with the correct attributes to be executed.
     */
    public Command parseInputCommand(String userString) {
        //if the program is in quiz mode, the input is not parsed
        if (isQuizMode.get()) {
            if (userString.equals("back")) {
                return new Command(CommandEnum.BACK, 0, userString);
            }
            return new Command(CommandEnum.QUIZ, 0, userString);
        } else if (isSettingUp) {
            return new Command(CommandEnum.SETUP, 0, userString);
        } else {
            return parser.parseInput(userString);
        }
    }

    /**
     * Executes the command.
     * @param currCommand the Command to be executed.
     * @return the program String response to be displayed.
     */
    public String executeCommand(Command currCommand) {
        historyList.add(currCommand.getUserString());

        String responseString;
        switch (currCommand.getType()) {
        case SETUP:
            return "setup";
        case HELP:
        case MENU:
            MenuCommand menuCommand = new MenuCommand(currCommand);
            return menuCommand.execute();
        case SELECT:
            return "You have selected chapter " + currCommand.getParameter() + " for the quiz!";
        case RESULT:
            ResultCommand resultCommand = new ResultCommand(currCommand, prevResult);
            return resultCommand.execute();
        case REPORT:
            PrintCommand printReportCommand = new PrintCommand(currCommand, userStats);
            return printReportCommand.execute();
        case HISTORY:
            HistoryCommand historyCommand = new HistoryCommand(currCommand, historyList);
            return historyCommand.execute();
        case UNDO:
            UndoCommand undoCommand = new UndoCommand(currCommand);
            return undoCommand.execute();
        case CLEAR:
            ClearCommand clearCommand = new ClearCommand(currCommand);
            return clearCommand.execute();
        case RESET:
            return "reset";
        case SAVE:
            return "save";
        case EXIT:
            ByeCommand byeCommand = new ByeCommand(currCommand);
            return byeCommand.execute();
        case PRINT:
            PrintCommand printQuizCommand = new PrintCommand(currCommand, quizList);
            return printQuizCommand.execute();
        case ARCHIVE:
            return "archive";
        case QUIZ:
            if (isNewQuiz.get()) {
                quizList = quizMaker.generateQuiz(selectedChapters, quizList);
                isNewQuiz.set(false);
                isQuizMode.set(true);
            }
            QuizCommand quizCommand = new QuizCommand(currCommand, quizList, questionNumber, isQuizMode, isNewQuiz);
            return quizCommand.execute();
        case BACK:
            if (isQuizMode.get()) {
                BackCommand backCommand = new BackCommand(currCommand, quizList, questionNumber);
                return backCommand.execute();
            }
            InvalidCommand invalidBackCommand = new InvalidCommand(currCommand);
            return invalidBackCommand.execute();
        default:
            InvalidCommand invalidCommand = new InvalidCommand(currCommand);
            return invalidCommand.execute();
        }
    }
}
