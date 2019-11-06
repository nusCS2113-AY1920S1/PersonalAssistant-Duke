package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.util.ArrayList;

public class ReviewCommand extends Command {

    private ArrayList<QuestionModel> quizList;

    /**
     * Create new command.
     *
     * @param inputs The inputs from the user
     * @param quizList The quizlist containing the questionModels.
     */
    public ReviewCommand(ArrayList<String> inputs, ArrayList<QuestionModel> quizList) {
        super(inputs);
        this.quizList = quizList;
    }

    @Override
    public String execute() {
        if (this.quizList.isEmpty()) {
            return "There is no current quiz available!";
        }
        int index = Integer.parseInt(inputs.get(1)) - 1;
        return quizList.get(index).getQuestion() + "\n"
                + "Correct answer : " + quizList.get(index).getAnswer() + "\n"
                + "Your answer : " + quizList.get(index).getUserAnswer() + "\n"
                + quizList.get(index).getRtlm().toString();
    }
}
