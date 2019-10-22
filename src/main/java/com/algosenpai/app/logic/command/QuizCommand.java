package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

public class QuizCommand extends Command {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    AtomicBoolean isQuizMode;

    AtomicBoolean isNewQuiz;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private QuizCommand(ArrayList<String> inputs) {
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
    public QuizCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList,
                       AtomicInteger questionNumber, AtomicBoolean isQuizMode, AtomicBoolean isNewQuiz) {
        this(inputs);
        this.quizList = quizList;
        this.isQuizMode = isQuizMode;
        this.questionNumber = questionNumber;
        this.isNewQuiz = isNewQuiz;
    }


    @Override
    public String execute() {
        quizList.get(questionNumber.get()).setAnswer(inputs.toString());
        questionNumber.incrementAndGet();

        if (questionNumber.get() < 10) {
            return quizList.get(questionNumber.get()).getQuestion();
        } else if (questionNumber.get() == 10) {
            int correctCount = 0;
            for (int i = 0; i < 10; i++) {
                QuestionModel currQuestion = quizList.get(i);
                if (currQuestion.checkAnswer()) {
                    correctCount++;
                }
            }
            questionNumber.set(0);
            isQuizMode.set(false);
            isNewQuiz.set(true);
            return "You got " + correctCount + "/10 questions correct!";
        }
        return "quiz";
    }
}
