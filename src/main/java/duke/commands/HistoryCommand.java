package duke.commands;

import duke.storage.Storage;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class HistoryCommand extends Command {
    private ArrayList<String> historyCommandsList;

    public HistoryCommand() {
        historyCommandsList = new ArrayList<String>();
    }

    public void addCommand(String commandStr) {
        if (!commandStr.equals("history")) {
            historyCommandsList.add(commandStr);
        }
    }

    public void clearHistory() {
        historyCommandsList.clear();
    }

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) {
        ui.showHistory(historyCommandsList);
    }
}
