package duke.logic.commands;

import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.Ui;
import duke.model.user.User;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * HistoryCommand is a public class that inherits form abstract class Command.
 * Tracks the history of all commands executed in the current session
 */
public class HistoryCommand extends Command {
    private ArrayList<String> historyCommandsList;

    /**
     * Constructor for HistoryCommand.
     */
    public HistoryCommand() {
        historyCommandsList = new ArrayList<String>();
    }

    /**
     * Add command to history of commands.
     * @param commandStr the command to be added to the list of executed commands thus far
     */
    public void addCommand(String commandStr) {
        if (!commandStr.equals("history")) {
            historyCommandsList.add(commandStr);
        }
    }

    /**
     * Clears the history of the commands executed thus far.
     */
    public void clearHistory() {
        historyCommandsList.clear();
    }

    /**
     * Executes the HistoryCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) {
        ui.showHistory(historyCommandsList);
    }
}
