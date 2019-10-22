package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class HistoryCommand extends Command {

    ArrayList<String> historyList;

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    private HistoryCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    private HistoryCommand(Command command) {
        this(command.getType(), command.getParameter(), command.getUserString());
    }

    public HistoryCommand(Command command, ArrayList<String> historyList) {
        this(command);
        this.historyList = historyList;
    }

    @Override
    public String execute() {
        StringBuilder history = new StringBuilder();
        history.append("Have you forgotten our conversation?\n");
        for (String str: historyList) {
            history.append(str).append("\n");
        }
        return history.toString();
    }
}
