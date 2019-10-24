package duke.logic.commands;

import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.commons.exceptions.DukeException;
import duke.model.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Command is the abstract base class for all the command objects
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 */
public abstract class Command {
    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected Calendar calendarDate = Calendar.getInstance();
    protected String currentDate = dateFormat.format(calendarDate.getTime());

    /**
     * this class is an abstract class that will change according to the inheritor.
     * @param tasks the TaskList object in which the task is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     * @param in the scanner object to handle secondary command IO
     */
    public abstract void execute(MealList tasks, Ui ui, Storage storage, User user,
                                 Scanner in, TransactionList transactions) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
