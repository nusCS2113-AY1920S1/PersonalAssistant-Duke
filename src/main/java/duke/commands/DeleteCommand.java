package duke.commands;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import duke.user.User;

/**
 * DeleteCommand is a public class that inherits from abstract class Command.
 * A DeleteCommand object encapsulates the index of the meal that is to be deleted.
 * @author Ivan Andika Lie
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * This is a constructor DeleteCommand.
     * @param index the index of meal to be deleted
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * The object will execute the "delete" command, updating the current tasks, ui, and storage in the process.
     * @param tasks the MealList object in which the the indexed meal is supposed to be deleted from
     * @param ui the ui object to display the user interface of a "delete" command
     * @param storage the storage object that stores the list of meals
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) {
        ArrayList<Meal> currentMeals = tasks.getMealsList(currentDate);
        Meal currentMeal = currentMeals.get(index - 1);
        tasks.delete(index);
        ui.showDeleted(currentMeal, currentMeals);
        storage.updateFile(tasks);
    }
}
