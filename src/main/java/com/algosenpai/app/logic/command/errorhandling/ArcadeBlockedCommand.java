package com.algosenpai.app.logic.command.errorhandling;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;

public class ArcadeBlockedCommand extends Command {

    /**
     * Create new command.
     * @param inputs User's input.
     */
    public ArcadeBlockedCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "This command is invalid during the arcade >.<";
    }
}
