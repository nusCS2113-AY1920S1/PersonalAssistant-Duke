package duke.command;

import duke.core.*;
import duke.exception.*;
import duke.task.*;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations required in the creation of a new recurring task.
 */

public class RecurringCommand extends Command {
    protected Recurring newRecurring;

    /**
     * Constructor for a DeadlineCommand. Creates a new Deadline based on the input
     * provided by the user.
     * @param description The description of the Deadline.
     * @param by The date of the Deadline.
     * @throws ParseException if the date does not follow the required format
     */
    public RecurringCommand(String description, String day, String date) throws DukeException, ParseException {
        newRecurring = new Recurring(description, day, date);
    }

    /**
     * This method will add the new Deadline to the TaskList and print a message
     * to the CLI, with the number of tasks now present.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        tasks.addItem(newRecurring);
        ui.printAddedTask(newRecurring.toString(), tasks.getNumberOfItems());
    }
}