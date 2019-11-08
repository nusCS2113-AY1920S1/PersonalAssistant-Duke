package duke.logic.commands;

import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

public class StatsCommand extends Command {

    public StatsCommand() {
    }

    /**
     * Constructor for StatsCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        if (user.getGoal() != null) {
            user.updateStats(meals);
        }
        ui.showStats(user);
    }
}
