package com.algosenpai.app;

import com.algosenpai.app.chapters.QuizGenerator;
import com.algosenpai.app.command.Command;
import com.algosenpai.app.logic.Question;

import java.util.ArrayList;

public class Logic {

    private Ui ui;
    private Parser parser;
    private QuizGenerator quizMaker = new QuizGenerator();

    //All variables for the settings of the program
    private boolean isNew = true;
    private boolean isSettingUp = false;
    private int level = 0;
    private String name;

    //All variables for the quiz function
    private boolean isQuizMode = false;
    private ArrayList<Question> quizList;
    private int questionNumber = 0;



    public Logic (Parser parser, Ui ui) {
        this.parser = parser;
        this.ui = ui;
    }

//    public Command parseInputCommand(String userString) {
//        return parser.parseInput(userString);
//    }

//    public static void executeCommand(Command currCommand) {
//        if (currCommand.getType().equals()
//    }

    /**
     * The main code to parse and execute commands from the user.
     *
     */
    public String parseInput(String userString) {
        if (isSettingUp) {
            isSettingUp = false;
            name = userString;
            String nameWelcome = "Hi " + name + "! Nice to meet you!"
            + " You can type 'start' to play the quiz." + " You are now level 0";
            return nameWelcome;
        }

        else if (isQuizMode) {
            quizList.get(questionNumber).setAnswer(userString);
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

                String endQuizMessage = "You got " + correctCount + "/10 questions correct!";
                quizList.clear();
                questionNumber = 0;
                return endQuizMessage;
            }
        }

        if (userString.equals("start")) {
            isQuizMode = true;
            quizList = makeQuiz(quizList);
            return quizList.get(0).getQuestion();
        } else if (userString.equals("hello")) {
            isSettingUp = true;
            return checkStatus();
        } else {
            return "NULL";
        }
    }

    /**
     * Checks whether it is the user's first time using the application.
     * If it is his/her first time, the isSettingUp boolean flag will be set to true.
     */
    public String checkStatus() {
        if (isNew) {
            isNew = false;
            return "Oh it seems that it is your first time here! Can I get your name?";
        }
        else {
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
    /**
     * Generates the quiz according to the chapters specified by the user.
     * By default, all the chapters will be selected.
     * @param quiz the ArrayList of Questions to be filled.
     * @return the ArrayList with all the questions generated
     */
    public ArrayList<Question> makeQuiz(ArrayList<Question> quiz) {
        return quizMaker.generateQuiz(1, quiz);
    }


}
