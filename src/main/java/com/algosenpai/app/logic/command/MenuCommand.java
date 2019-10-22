package com.algosenpai.app.logic.command;

public class MenuCommand extends Command {

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private MenuCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    public MenuCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
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
