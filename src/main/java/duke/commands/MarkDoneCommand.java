package duke.commands;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import duke.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of meal to be marked as done.
 * @author Ivan Andika Lie
 */
public class MarkDoneCommand extends Command {
    private int index;

    /**
     * This is a constructor for MarkDoneCommand.
     * @param index the index of meal to be marked done
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * The object will execute the "mark done" command, updating the current meals, ui, and storage in the process.
     * @param tasks the MealList object to be marked done
     * @param ui the ui object to display the user interface of an "mark done" command
     * @param storage the storage object that stores the list of meals
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) {
        ArrayList<Meal> currentMeals = tasks.getMealsList(currentDate);
        Meal currentMeal = currentMeals.get(index - 1);
        currentMeal.markAsDone();
        storage.updateFile(tasks);
        ui.showDone(currentMeal);
    }
}
