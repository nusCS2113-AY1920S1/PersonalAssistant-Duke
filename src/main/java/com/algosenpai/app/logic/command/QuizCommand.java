package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class QuizCommand extends Command {

    /**
     * Initializes commands for quiz.
     * @param inputs input from user.
     */
    public QuizCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "";
    }
}
