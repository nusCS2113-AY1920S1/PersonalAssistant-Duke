package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;

public class ClearCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public ClearCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "clear";
    }
}
