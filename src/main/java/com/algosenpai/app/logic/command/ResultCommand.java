package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class ResultCommand extends Command {

    private int results;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private ResultCommand(ArrayList<String> inputs) {
        super(inputs);
    }


    public ResultCommand(ArrayList<String> inputs, int results) {
        this(inputs);
        this.results = results;
    }

    @Override
    public String execute() {
        return "You got " + results + "/10 questions correct for the last attempt";
    }
}
