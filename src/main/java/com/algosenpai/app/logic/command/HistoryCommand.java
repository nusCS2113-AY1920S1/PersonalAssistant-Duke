package com.algosenpai.app.logic.command;

import java.util.ArrayList;

public class HistoryCommand extends Command {

    ArrayList<String> historyList;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    private HistoryCommand(ArrayList<String> inputs) {
        super(inputs);
    }


    public HistoryCommand(ArrayList<String> inputs, ArrayList<String> historyList) {
        this(inputs);
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
