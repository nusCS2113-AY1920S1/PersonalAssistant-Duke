package duke.commands;

import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.exceptions.DukeException;
import duke.user.User;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;

/**
 * ListCommand is a public class that inherits from abstract class Command.
 * @author Ivan Andika Lie
 */
public class ListCommand extends Command {

    private String date;

    public ListCommand() {
    }

    public ListCommand(String date) throws DukeException {
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        currentDate = dateFormat.format(temp);
    }

    /**
     * The object will execute the "list" command.
     * @param tasks the TaskList object in which the task(s) is supposed to be listed
     * @param ui the ui object to display the user interface of an "list" command
     * @param storage the storage object that stores the list of tasks
     */

    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user) throws DukeException {
        ui.showCalorie(user);
        ArrayList<Meal> currentMeals = tasks.getMealsList(currentDate);
        if (!tasks.checkDate(currentDate)) {
            throw new DukeException("There isn't any food on " + currentDate);
        }
        ui.showList(currentMeals);
        ui.showRemainingCalorie(currentMeals, user);
    }
}
