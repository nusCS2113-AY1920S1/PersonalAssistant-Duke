package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class UndoCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public UndoCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return inputs.toString();
    }
}
