package duke.commands;

import java.util.ArrayList;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.user.User;

/**
 * AddCommand is a public class that inherits from abstract class Command.
 * An AddCommand object encapsulates the current task that is to be added.
 * @author Ivan Andika Lie
 */
public class AddCommand extends Command {
    private Meal meal;

    /**
     * This is a constructor for AddCommand which create a new AddCommand object with
     * the task specified as the instance field task.
     * @param meal The task to be added.
     */
    public AddCommand(Meal meal) {
        this.meal = meal;
    }

    /**
     * The object will execute the "add" command, updating the current tasks, ui, and storage in the process.
     * @param meals the TaskList object in which the task is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     */
  
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user) {
        meals.addMeal(this.meal);
        ui.showAdded(this.meal, meals.getMeals(this.meal.getDate()), user, this.meal.getDate());
        storage.updateFile(meals.getMealTracker());
    }
}
