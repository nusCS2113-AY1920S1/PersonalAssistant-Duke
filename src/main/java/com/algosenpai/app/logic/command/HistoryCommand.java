package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.constant.Commands;

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
        if (inputs.size() != 2) {
            return "OOPS!!! Error occurred. Please key in the number of commands"
                    + "you'd like to view in the following format: e.g history 5";
        }
        if (Commands.isInteger(inputs.get(1))) {
            int num = Integer.parseInt(inputs.get(1));
            if (num > historyList.size()) {
                return "OOPS!!! Error occurred. You don't have that many past commands!";
            }
            StringBuilder history = new StringBuilder();
            history.append("Have you forgotten our conversation?\n");
            for (int i = historyList.size() - (num + 1); i < historyList.size() - 1; i++) {
                history.append(historyList.get(i)).append("\n");
            }
            return history.toString();
        } else {
            return "OOPS!!! Error occurred. Please key in a valid number of commands you'd like to view!";
        }
    }
}
