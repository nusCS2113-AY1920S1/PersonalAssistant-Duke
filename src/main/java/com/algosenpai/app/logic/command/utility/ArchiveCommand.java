package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;

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
        archiveList.add(quizList.get(Integer.parseInt(inputs.get(1))).copy());
        return "Archived!";
    }
}
