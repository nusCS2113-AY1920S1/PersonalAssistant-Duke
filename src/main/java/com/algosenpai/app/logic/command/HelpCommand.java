package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class HelpCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public HelpCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        switch (inputs.get(1)) {
        case "1":
            return "Try solving these problems on Kattis:\n"
                    + "cups, lineup, mjehuric, sidewayssorting";
        case "2":
            return "Try solving these problems on Kattis:\n"
                    + "coconut, integerlists, joinstrings";
        default:
            return "Code more!";
        }
    }
}
