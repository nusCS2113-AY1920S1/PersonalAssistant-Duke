package com.algosenpai.app.logic.command;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public class Command {

    private CommandEnum commandType;
    private String userString;
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

}

