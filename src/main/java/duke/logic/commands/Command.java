package duke.logic.commands;

import duke.model.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.commons.exceptions.DukeException;
import duke.model.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Command is the abstract base class for all the command objects.
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 */
public abstract class Command {
    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected Calendar calendarDate = Calendar.getInstance();
    protected String currentDate = dateFormat.format(calendarDate.getTime());

    /**
     * This class is an abstract class that will change according to the inheritor.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     * @throws DukeException when there is an error
     */
    public abstract void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
