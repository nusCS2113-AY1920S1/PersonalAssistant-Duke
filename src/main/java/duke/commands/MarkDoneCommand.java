package duke.commands;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;
import duke.user.User;

/**
 * MarkDoneCommand is a public class that inherits form abstract class Command.
 * A MarkDoneCommand object encapsulates the index of task to be marked as done.
 * @author Ivan Andika Lie
 */
public class MarkDoneCommand extends Command {
    private int index;

    /**
     * This is a constructor for MarkDoneCommand.
     * @param index the index of task to be marked done
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * The object will execute the "mark done" command, updating the current tasks, ui, and storage in the process.
     * @param tasks the TaskList object to be marked done
     * @param ui the ui object to display the user interface of an "mark done" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) {
        ArrayList<Meal> currentMeals = tasks.getMeals(currentDate);
        Meal currentMeal = currentMeals.get(index - 1);
        currentMeal.markAsDone();
        storage.updateFile(tasks.getMealTracker());
        ui.showDone(currentMeal);
    }
}
