package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BackCommand extends Command {

    ArrayList<QuestionModel> quizList;

    AtomicInteger questionNumber;

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private BackCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    private BackCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
    }

    /**
     * Initializes back command to go to previous question in the quiz.
     * @param command back command.
     * @param quizList quiz.
     * @param questionNumber question number.
     */
    public BackCommand(Command command, ArrayList<QuestionModel> quizList,
                       AtomicInteger questionNumber) {
        this(command);
        this.quizList = quizList;
        this.questionNumber = questionNumber;
    }

    @Override
    public String execute() {
        return quizList.get(questionNumber.decrementAndGet()).getQuestion();
    }
}
