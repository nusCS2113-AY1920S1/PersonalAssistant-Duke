package com.algosenpai.app.logic.command;

import java.io.IOException;
import java.util.ArrayList;

public class BlockedCommand extends Command {

    /**
     * Create new command.
     *
     * @param inputs The user's inputs.
     */
    public BlockedCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() throws IOException {
        return "This command is invalid during the quiz! >.<";
    }
}
