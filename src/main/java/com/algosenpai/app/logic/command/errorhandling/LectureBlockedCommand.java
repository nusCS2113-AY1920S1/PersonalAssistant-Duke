package com.algosenpai.app.logic.command.errorhandling;

import com.algosenpai.app.logic.command.Command;

import java.io.IOException;
import java.util.ArrayList;

public class LectureBlockedCommand extends Command {


    /**
     * Create new command.
     *
     * @param inputs The user's input.
     */
    public LectureBlockedCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() throws IOException {
        return "This command is invalid during the lecture! >.<";
    }
}
