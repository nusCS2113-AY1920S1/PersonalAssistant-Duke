package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BackCommand extends Command {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private BackCommand(ArrayList<String> inputs) {
        super(inputs);
    }


    /**
     * Initializes back command to go to previous question in the quiz.
     * @param inputs user inputs.
     * @param quizList quiz.
     * @param questionNumber question number.
     */
    public BackCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList,
                       AtomicInteger questionNumber) {
        this(inputs);
        this.quizList = quizList;
        this.questionNumber = questionNumber;
    }

    @Override
    public String execute() {
        return quizList.get(questionNumber.decrementAndGet()).getQuestion();
    }
}
