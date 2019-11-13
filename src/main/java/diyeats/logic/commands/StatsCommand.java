package diyeats.logic.commands;

import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author Fractalisk

/**
 * StatsCommand is a public class that inherits from abstract class Command.
 * Displays the user's dietary statistics, provided goal exists.
 */
public class StatsCommand extends Command {

    /**
     * Constructor for StatsCommand.
     */
    public StatsCommand() {
    }

    /**
     * Executes StatsCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        ui.showLine();
        if (user.getGoal() != null) {
            user.updateStats(meals);
        }
        ui.showStats(user);
        ui.showLine();
    }
}
