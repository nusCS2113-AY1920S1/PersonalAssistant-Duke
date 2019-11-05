package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizTestCommand extends QuizCommand {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    AtomicBoolean isQuizMode;

    AtomicBoolean isNewQuiz;

    int chapterNumber;

    private UserStats userStats;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private QuizTestCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Initializes quiz command to start quiz.
     * @param inputs user inputs.
     * @param quizList quiz.
     * @param questionNumber question number.
     * @param isQuizMode is quiz mode.
     * @param isNewQuiz is quiz initialize.
     */
    public QuizTestCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList,
                           AtomicInteger questionNumber, AtomicBoolean isQuizMode, AtomicBoolean isNewQuiz,
                           int chapterNumber,UserStats userStats) {
        this(inputs);
        this.quizList = quizList;
        this.isQuizMode = isQuizMode;
        this.questionNumber = questionNumber;
        this.isNewQuiz = isNewQuiz;
        this.chapterNumber = chapterNumber;
        this.userStats = userStats;
    }


    @Override
    public String execute() {
        int shownIndex = questionNumber.incrementAndGet();
        int ourIndex = shownIndex - 1;
        if (shownIndex == 1) {
            return (shownIndex) +  ".\t" + quizList.get(ourIndex).getQuestion()
                    + "\n Your answer: ";
        } else if (shownIndex <= 10) {
            //add the userinput that has been parsed as his answer.
            if (inputs.size() > 0) {
                //to end the quiz in the quizmode
                if (inputs.get(0).equals("end")) {
                    isQuizMode.set(false);
                    return calculateScore();
                }
                String userAnswer = extractUserAnswerFromInput();
                //Sets the answer to the previous question.
                quizList.get(ourIndex - 1).setUserAnswer(userAnswer);
            }
            return (shownIndex) +  ".\t" + quizList.get(ourIndex).getQuestion()
                    + "\n Your answer: ";
        } else {
            String userAnswer = extractUserAnswerFromInput();
            //Sets the answer to the previous question.
            quizList.get(ourIndex - 1).setUserAnswer(userAnswer);
            reset();
            return calculateScore();
        }
    }

    /**
     * Parses the user input into a string format.
     * @return The string containing the user's answer.
     */
    private String extractUserAnswerFromInput() {
        StringBuilder answer = new StringBuilder();
        System.out.println("Here");
        for (int i = 0; i < inputs.size() - 1; i++) {
            answer.append(inputs.get(i)).append(" ");
        }
        answer.append(inputs.get(inputs.size() - 1));
        return answer.toString();
    }


    private void reset() {
        questionNumber.set(0);
        isQuizMode.set(false);
        isNewQuiz.set(true);
    }

    private String calculateScore() {
        int userQuizScore = 0;
        for (QuestionModel question : quizList) {
            if (question.checkAnswer()) {
                userQuizScore++;
            }
        }

        // Updating all the user stats one shot in here
        userStats.updateChapter(chapterNumber,10,userQuizScore);
        userStats.setUserExp(userStats.getUserExp() + userQuizScore);
        // Update level, each level is double of previous, so we use log base 2.
        if (userStats.getUserExp() == 0) {
            userStats.setUserLevel(0);
        } else {
            userStats.setUserLevel((int)(Math.log(userStats.getUserExp() / 10.0) / Math.log(2) + 1));
        }
        userStats.saveUserStats("UserData.txt");
        // End of updating

        return "You got " + userQuizScore + "/10 questions correct!\n"
                + "You have gained " + userQuizScore + " EXP points!";
    }
}