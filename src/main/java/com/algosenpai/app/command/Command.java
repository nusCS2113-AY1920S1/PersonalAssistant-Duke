package com.algosenpai.app.command;


public class Command {

    private CommandEnum commandType;

    private int commandParameter;
    /**
     * Create new command.
     */
    public Command(CommandEnum commandType, int specifier) {
        this.commandType = commandType;
        this.commandParameter = specifier;
    }

    public CommandEnum getType() {
        return this.commandType;
    }

}
