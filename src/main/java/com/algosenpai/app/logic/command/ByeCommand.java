package com.algosenpai.app.logic.command;

public class ByeCommand extends Command {

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private ByeCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    public ByeCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
    }

    @Override
    public String execute() {
        return "Bye!";
    }
}
