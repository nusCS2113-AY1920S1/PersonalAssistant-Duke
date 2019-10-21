package com.algosenpai.app.logic;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.CommandEnum;
import com.algosenpai.app.logic.chapters.QuizGenerator;

import java.util.ArrayList;

public class Logic {

    private Parser parser;
    private QuizGenerator quizMaker = new QuizGenerator();

    //All variables for the settings of the program
    private boolean isNew = true;
    private boolean isSettingUp = false;
    private int level = 0;
    private String name;

    //All variables for the quiz function
    private int selectedChapters = 0;
    private boolean isQuizMode = false;
    private ArrayList<Question> quizList;
    private int questionNumber = 0;
    private int prevResult = 0;

    public Logic(Parser parser) {
        this.parser = parser;
    }

    /**
     * Parses the user input for the command to be created and executed.
     * @param userString the user input from the GUI.
     * @return the Command object with the correct attributes to be executed.
     */
    public Command parseInputCommand(String userString) {
        //if the program is in quiz mode, the input is not parsed
        if (isQuizMode) {
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
        String responseString;
        switch (currCommand.getType()) {
        case SETUP:
            if (isNew) {
                responseString = checkStatus();
                return responseString;
            } else {
                responseString = "Are you a boy or a girl?";
                isSettingUp = false;
                return responseString;
            }
        case MENU:
            String allCommands = "These are all the commands available: \n" +
                    "MENU, " + "START, " + "SELECT, " + "RESULT, " +
                    "REPORT, " + "BACK, " + "HISTORY, " + "UNDO, \n" +
                    "CLEAR, " + "RESET, " + "SAVE, " + "HELP, " +
                    "EXIT, " + "PRINT, " + "ARCHIVE, " + "INVALID";
            return allCommands;
        case START:
            isQuizMode = true;
            quizList = quizMaker.generateQuiz(selectedChapters, quizList);
            return quizList.get(0).getQuestion();
        case SELECT:
            selectedChapters = currCommand.getParameter();
            responseString = "You have selected chapter " + currCommand.getParameter() +
                        " for the quiz!";
            return responseString;
        case RESULT:
            responseString = "You got " + prevResult + "/10 questions correct for the last attempt";
            return responseString;
        case REPORT:

        case BACK:

        case HISTORY:

        case UNDO:

        case CLEAR:

        case RESET:

        case SAVE:

        case HELP:

        case EXIT:
            responseString = "exit";
            return responseString;
        case PRINT:

        case ARCHIVE:

        case QUIZ:
            quizList.get(questionNumber).setAnswer(currCommand.getUserString());
            questionNumber++;

            if (questionNumber < 10) {
                return quizList.get(questionNumber).getQuestion();
            } else if (questionNumber == 10) {
                isQuizMode = false;
                int correctCount = 0;

                for (int i = 0; i < 10; i++) {
                    Question currQuestion = quizList.get(i);
                    if (currQuestion.checkAnswer()) {
                        correctCount++;
                    }
                }

                this.prevResult = correctCount;
                quizList.clear();
                questionNumber = 0;
                String endQuizMessage = "You got " + correctCount + "/10 questions correct!";
                return endQuizMessage;
            }
        default:
            return "INVALID";
        }
    }


    /**
     * Checks whether it is the user's first time using the application.
     * If it is his/her first time, the isSettingUp boolean flag will be set to true.
     */
    public String checkStatus() {
        if (isNew) {
            isSettingUp = true;
            isNew = false;
            return "Oh it seems that it is your first time here! Can I get your name?";
        } else {
            return " Welcome back " + name + " You are currently level " + level;
        }
    }

    /**
     * Sets the name of the user.
     * @param userName the name input by the user.
     */
    public void setName(String userName) {
        name = userName;
    }

}
