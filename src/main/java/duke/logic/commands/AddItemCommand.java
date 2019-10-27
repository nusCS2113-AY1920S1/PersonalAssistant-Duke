package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.util.Scanner;

/**
 * AddItemCommand is a public class that inherits from abstract class Command.
 * An AddItemCommand object encapsulates the current meal that is to be added.
 */
public class AddItemCommand extends Command {
    private Meal meal;

    /**
     * Constructor for AddItemCommand.
     * @param meal The meal to be added.
     */
    public AddItemCommand(Meal meal) {
        this.meal = meal;
    }

    public AddItemCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Execute the AddItemCommand.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param storage the storage object that stores the list of meals
     */

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        meals.addStoredItem(this.meal);
        ui.showAddedItem(this.meal);
        storage.updateDefaults(meals);
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }

}