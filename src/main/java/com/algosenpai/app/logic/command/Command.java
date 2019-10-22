package com.algosenpai.app.logic.command;

import java.util.ArrayList;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public class Command {

    private CommandEnum commandType;
    String userString;
    private int commandParameter;

    /**
     * Create new command.
     */
    public Command(CommandEnum commandType, int specifier, String input) {
        this.commandType = commandType;
        this.commandParameter = specifier;
        this.userString = input;
    }

    public CommandEnum getType() {
        return this.commandType;
    }

    public String getUserString() {
        return this.userString;
    }

    public int getParameter() {
        return this.commandParameter;
    }

    public String execute() {
        return "";
    }

    public ArrayList<String> parser() {
        return new ArrayList<>();
    }

}

