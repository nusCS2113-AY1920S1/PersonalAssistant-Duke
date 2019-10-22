package com.algosenpai.app.logic.command;

public class InvalidCommand extends Command {

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private InvalidCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    public InvalidCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
    }

    @Override
    public String execute() {
        return "!!!?";
    }
}
