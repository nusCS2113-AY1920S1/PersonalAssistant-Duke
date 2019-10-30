package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;

import java.io.IOException;
import java.util.ArrayList;

public class ReviewCommand extends Command {

    ArrayList<QuestionModel> quizList;

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
    public String execute() throws IOException {
        return quizList.get(Integer.parseInt(inputs.get(1))).getRtlm().toString();
    }
}
