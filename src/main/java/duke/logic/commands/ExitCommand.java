package duke.logic.commands;

import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.util.Scanner;

/**
 * ExitCommand is a public class that extends from the abstract class Command.
 */
public class ExitCommand extends Command {
    /**
     * isExit() is a function that will return true if called, to indicate the the program is going to exit.
     * @return <code>true</code> if the function is called
     */
    public boolean isExit() {
        return true;
    }

    /**
     * Executes the exit command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showBye();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
