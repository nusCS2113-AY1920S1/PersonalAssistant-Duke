package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizNextCommand extends QuizCommand {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    /**
     * Initializes back command to go to previous question in the quiz.
     * @param inputs user inputs.
     * @param quizList quiz.
     * @param questionNumber question number.
     */
    public QuizNextCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList,
                           AtomicInteger questionNumber) {
        super(inputs);
        this.quizList = quizList;
        this.questionNumber = questionNumber;
    }

    @Override
    public String execute() {
        if (inputs.get(0).equals("next") && questionNumber.get() < 9) {
            questionNumber.incrementAndGet();
        } else if (inputs.get(0).equals("back") && questionNumber.get() > 0) {
            questionNumber.decrementAndGet();
        }
        return quizList.get(questionNumber.get()).getQuestion()
                + "\n Your answer: "
                + quizList.get(questionNumber.get()).getUserAnswer();
    }
}
