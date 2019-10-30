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
        if (inputs.get(1).equals("next")) {
            questionNumber.incrementAndGet();
        }
        if (inputs.get(1).equals("back")) {
            questionNumber.decrementAndGet();
        }
        return quizList.get(questionNumber.get()).getQuestion()
                + "\n"
                + quizList.get(questionNumber.get()).getUserAnswer();
    }
}
