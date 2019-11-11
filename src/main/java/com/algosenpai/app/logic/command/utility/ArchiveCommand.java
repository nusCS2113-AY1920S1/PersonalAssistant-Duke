package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.parser.Parser;

import java.util.ArrayList;

public class ArchiveCommand extends Command {

    private ArrayList<QuestionModel> quizList;
    private ArrayList<QuestionModel> archiveList;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public ArchiveCommand(ArrayList<String> inputs,
                          ArrayList<QuestionModel> quizList, ArrayList<QuestionModel> archiveList) {
        super(inputs);
        this.quizList = quizList;
        this.archiveList = archiveList;

    }

    @Override
    public String execute() {
        String errorMessage = "Please use the proper format. Try `archive x` where x is a number between 1 and 10.";
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
        archiveList.add(quizList.get(index).copy());
        return "Archived!";
    }
}
