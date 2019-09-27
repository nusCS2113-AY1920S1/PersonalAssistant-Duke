package duke.commands;
import java.util.ArrayList;

import duke.tasks.Meal;
import duke.tasks.mealList;
import duke.ui.Ui;
import duke.storage.Storage;

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
    public void execute(mealList meals, Ui ui, Storage storage) {
        ArrayList<Meal> currentMeals = meals.getMeals(meal.getDate());
        currentMeals.add(meal);
        ui.showAdded(meal, currentMeals);
        storage.updateFile(meals.getMealTracker());
    }
}
