package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class ByeCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public ByeCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "Bye!";
    }
}
