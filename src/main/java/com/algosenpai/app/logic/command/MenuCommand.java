package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class MenuCommand extends Command {

    /**
     * Initializes command to show available commands.
     * @param inputs user input.
     */
    public MenuCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        return "Senpai will teach you! Try these commands\n"
                + "MENU\n"
                + "START\n"
                + "SELECT\n"
                + "RESULT\n"
                + "REPORT\n"
                + "BACK\n"
                + "HISTORY\n"
                + "UNDO\n"
                + "CLEAR\n"
                + "RESET\n"
                + "SAVE\n"
                + "HELP\n"
                + "EXIT\n"
                + "PRINT\n"
                + "ARCHIVE\n"
                + "INVALID\n";
    }
}
