package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import duke.user.User;

/**
 * DeleteCommand is a public class that inherits from abstract class Command.
 * A DeleteCommand object encapsulates the index of meal and date of the meal that is to be deleted.
 */
public class DeleteCommand extends Command {
    private int index;
    private final String helpText = "Please follow: delete <index> /date <date> or "
            + "delete <index> to delete for current day.";

    /**
     * This is a constructor DeleteCommand.
     * @param indexStr the index of meal on the date to be deleted.
     * @param date Date of meal to be deleted.
     */
    public DeleteCommand(String indexStr, String date) throws DukeException {
        this(indexStr);
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Unable to parse input " + date + " as a date. " + helpText);
        }
        currentDate = dateFormat.format(temp);
    }

    public DeleteCommand(String indexStr) throws DukeException {
        try {
            this.index = Integer.parseInt(indexStr.strip());
        } catch (NumberFormatException nfe) {
            throw new DukeException("Unable to parse input " + indexStr + " as integer index. " + helpText);
        }
    }

    /**
     * The object will execute the "delete" command, updating the current tasks, ui, and storage in the process.
     * @param mealList the TaskList object in which the the indexed task is supposed to be deleted from
     * @param ui the ui object to display the user interface of a "delete" command
     * @param storage the storage object that stores the list of tasks
     */
    @Override
    public void execute(MealList mealList, Ui ui, Storage storage, User user) throws DukeException {
        if (index <= 0 || index > mealList.getMeals(currentDate).size()) {
            throw new DukeException("Index provided out of bounds for list of meals on " + currentDate);
        }
        Meal currentMeal = mealList.delete(currentDate, index);
        ui.showDeleted(currentMeal, mealList.getMeals(currentDate));
        storage.updateFile(mealList.getMealTracker());
    }
}
