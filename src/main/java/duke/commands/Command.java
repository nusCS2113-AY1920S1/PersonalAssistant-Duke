package duke.commands;

import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.exceptions.DukeException;
import duke.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Command is the abstract base class for all the command objects
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 * @author Ivan Andika Lie
 */
public abstract class Command {
    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected Calendar calendarDate = Calendar.getInstance();
    protected String currentDate = dateFormat.format(calendarDate.getTime());
    /**
     * this class is an abstract class the will the specific command specified.
     * @param tasks the TaskList object in which the task is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     */

    public abstract void execute(MealList tasks, Ui ui, Storage storage, User user) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
