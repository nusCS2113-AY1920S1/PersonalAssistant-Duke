package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
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
        if (inputs.size() != 2) {
            return "Please use the proper format. Try `review x` where x is a number between 1 and 10.";
        }
        int index = Integer.parseInt(inputs.get(1)) - 1;
        if (index < 0 || index > 10) {
            return "Please use the proper format. Try `review x` where x is a number between 1 and 10.";
        }
        return quizList.get(index).getQuestion() + "\n"
                + "Correct answer : " + quizList.get(index).getAnswer() + "\n"
                + "Your answer : " + quizList.get(index).getUserAnswer() + "\n"
                + quizList.get(index).getRtlm().toString();
    }
}
