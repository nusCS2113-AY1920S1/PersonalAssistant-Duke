package com.algosenpai.app.logic.command;

import java.io.IOException;
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
    public String execute() throws IOException {
        return "This command is invalid during the quiz! >.<";
    }
}
