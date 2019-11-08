package DIYeats.logic.commands;

import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Wallet;
import DIYeats.storage.Storage;

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
}
