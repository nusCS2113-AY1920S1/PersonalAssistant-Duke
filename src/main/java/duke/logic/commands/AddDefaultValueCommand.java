package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;
import duke.storage.Storage;

//@@author Fractalisk
/**
 * AddItemCommand is a public class that inherits from abstract class Command.
 * An AddItemCommand object encapsulates the current meal that is to be added.
 */
public class AddDefaultValueCommand extends Command {
    private Meal meal;

    /**
     * Constructor for AddItemCommand.
     * @param meal The meal to be added.
     */
    public AddDefaultValueCommand(Meal meal) {
        this.meal = meal;
    }

    public AddDefaultValueCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Execute the AddItemCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        meals.addDefaultValues(this.meal);
        ui.showAddedItem(this.meal);
        try {
            storage.updateDefaults(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}