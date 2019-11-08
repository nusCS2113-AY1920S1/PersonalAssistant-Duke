package com.algosenpai.app.logic.command.errorhandling;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;

public class QuizBlockedCommand extends Command {

    /**
     * Create new command.
     *
     * @param inputs The user's inputs.
     */
    public QuizBlockedCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "This command is invalid during the quiz! >.<";
    }
}
