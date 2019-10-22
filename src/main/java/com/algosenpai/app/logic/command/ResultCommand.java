package com.algosenpai.app.logic.command;

public class ResultCommand extends Command {

    private int results;

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private ResultCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    public ResultCommand(Command command, int results) {
        this(command.getType(), command.getParameter(), command.getUserString());
        this.results = results;
    }

    @Override
    public String execute() {
        return "You got " + results + "/10 questions correct for the last attempt";
    }
}
