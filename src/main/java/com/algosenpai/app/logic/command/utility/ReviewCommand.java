package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;

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
        String errorMessage = "Please use the proper format. Try `review x` where x is a number between 1 and 10.";
        if (this.quizList.isEmpty()) {
            return "There is no current quiz available!";
        }
        if (inputs.size() != 2 || !Parser.isInteger(inputs.get(1))) {
            return errorMessage;
        }
        if (!Parser.isInteger(inputs.get(1))) {
            return errorMessage;
        }
        int index = Integer.parseInt(inputs.get(1)) - 1;
        if (index < 0 || index > 9) {
            return errorMessage;
        }
        return quizList.get(index).getQuestion() + "\n"
                + "Correct answer : " + quizList.get(index).getAnswer() + "\n"
                + "Your answer : " + quizList.get(index).getUserAnswer() + "\n"
                + quizList.get(index).getRtlm().toString();
    }
}
