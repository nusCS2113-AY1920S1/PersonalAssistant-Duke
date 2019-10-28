package duke.logic.commands;

import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.meal.MealList;
import duke.model.user.User;

import java.util.ArrayList;

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

    public HistoryCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
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
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     */

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        ui.showHistory(historyCommandsList);
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
