package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizTestCommand extends QuizCommand {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    AtomicBoolean isQuizMode;

    AtomicBoolean isNewQuiz;

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
                           AtomicInteger questionNumber, AtomicBoolean isQuizMode, AtomicBoolean isNewQuiz) {
        this(inputs);
        this.quizList = quizList;
        this.isQuizMode = isQuizMode;
        this.questionNumber = questionNumber;
        this.isNewQuiz = isNewQuiz;
    }


    @Override
    public String execute() {
        if (quizList.size() == 0) {
            return "You need to select a chapter first: select <chapter name>";
        }
        if (isQuizMode.get()) {
            if (!inputs.get(0).equals("quiz") || inputs.size() != 2) {
                return "You are taking a quiz. Please prefix commands with quiz: quiz <answer | back | next>";
            }
            quizList.get(questionNumber.get()).setUserAnswer(inputs.get(1));
            questionNumber.incrementAndGet();
        }

        isNewQuiz.set(false);
        isQuizMode.set(true);

        if (questionNumber.get() < 10) {
            return quizList.get(questionNumber.get()).getQuestion()
                    + "\n"
                    + quizList.get(questionNumber.get()).getUserAnswer();
        }
        int correctCount = calculateScore();
        reset();
        return "You got " + correctCount + "/10 questions correct!\n"
                + "You have gained " + correctCount + " EXP points!";

    }

    private void reset() {
        questionNumber.set(0);
        isQuizMode.set(false);
        isNewQuiz.set(true);
    }

    private int calculateScore() {
        int correctCount = 0;
        for (int i = 0; i < 10; i++) {
            QuestionModel currQuestion = quizList.get(i);
            if (currQuestion.checkAnswer()) {
                correctCount++;
            }
        }
        return correctCount;
    }
}
