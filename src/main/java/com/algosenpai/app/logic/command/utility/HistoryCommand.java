package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.exceptions.HistoryExceptions;
import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;

public class HistoryCommand extends Command {

    private ArrayList<String> historyList;

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
        try {
            HistoryExceptions.checkInput(inputs);
            String argument = inputs.get(1);
            HistoryExceptions.checkArgument(historyList, argument);
            int num = Integer.parseInt(argument);
            StringBuilder history = new StringBuilder();
            history.append("Have you forgotten our conversation?\n");
            for (int i = historyList.size() - (num + 1); i < historyList.size() - 1; i++) {
                history.append(historyList.get(i)).append("\n");
            }
            return history.toString();
        } catch (HistoryExceptions e) {
            return e.getMessage();
        }
    }
}
