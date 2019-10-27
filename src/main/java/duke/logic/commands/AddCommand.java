package duke.logic.commands;

import java.util.ArrayList;
import java.util.Scanner;

import duke.commons.exceptions.DukeException;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current meal that is to be added.
 */
public class AddCommand extends Command {
    private Meal meal;
    private int cost;
    /**
     * Constructor for AddCommand.
     * the meal specified as the instance field meal.
     * @param meal The meal to be added.
     */

    public AddCommand(Meal meal, int cost) {
        this.meal = meal;
        this.cost = cost;
    }

    public AddCommand(boolean flag, String message) {
        this.isFail = true;
        this.error = message;
    }

    /**
     * Executes add command.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param storage the storage object that stores the list of meals
     * @throws DukeException if there is a parsing error
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ui.showLine();
        try {
            meals.addMeals(this.meal);
            wallet.addTransactions(cost,this.meal.getDate());
            ArrayList<Meal> mealData = meals.getMealTracker().get(this.meal.getDate());
            ui.showAdded(this.meal, mealData, user, this.meal.getDate());
            storage.updateFile(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public void execute2(MealList meals, Storage storage, User user, Wallet wallet) {
    }
}
