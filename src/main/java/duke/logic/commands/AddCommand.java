package duke.logic.commands;

import java.util.ArrayList;
import java.util.Scanner;

import duke.commons.exceptions.DukeException;
import duke.model.Meal;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current meal that is to be added.
 * @author Ivan Andika Lie
 */
public class AddCommand extends Command {
    private Meal meal;

    /**
     * This is a constructor for AddCommand which create a new AddCommand object with
     * the meal specified as the instance field meal.
     * @param meal The meal to be added.
     */
    public AddCommand(Meal meal) {
        this.meal = meal;
    }

    /**
     * The object will execute the "add" command, updating the current tasks, ui, and storage in the process.
     * @param meals the MealList object in which the meal is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of meals
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user,
                        Scanner in, TransactionList transactions) throws DukeException {
        meals.addMeals(this.meal);
        ArrayList<Meal> mealData = meals.getMealTracker().get(this.meal.getDate());
        ui.showAdded(this.meal, mealData, user, this.meal.getDate());
        storage.updateFile(meals);
    }
}
