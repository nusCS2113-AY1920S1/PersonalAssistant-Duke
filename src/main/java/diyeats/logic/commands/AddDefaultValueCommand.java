package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

//@@author Fractalisk

/**
 * AddDefaultValueCommand is a public class that inherits from abstract class Command.
 * An AddDefaultValueCommand object encapsulates the current meal that is to be added as a defaultvalue.
 */
public class AddDefaultValueCommand extends Command {
    private Meal meal;

    /**
     * Constructor for AddDefaultValueCommand.
     * @param meal The meal to be added.
     */
    public AddDefaultValueCommand(Meal meal) {
        this.meal = meal;
    }

    // This constructor is called if there are issues parsing user input.
    public AddDefaultValueCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Execute the AddDefaultValueCommand.
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
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}