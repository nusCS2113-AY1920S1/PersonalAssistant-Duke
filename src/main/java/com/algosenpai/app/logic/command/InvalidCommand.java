package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class InvalidCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public InvalidCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "!!!?";
    }
}
