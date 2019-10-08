package duke.command;

import duke.core.*;
import duke.exception.*;
import duke.task.*;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations required in the creation of a new Todo.
 */

public class FixCommand extends Command {
    protected FixDurationTask newFixDurationTask;

    /**
     * Constructor for a TodoCommand. Creates a new Todo based on the input
     * provided by the user.
     * @param description The description of the Deadline.
     */
    public FixCommand(String description, String by) throws ParseException {
        newFixDurationTask = new FixDurationTask(description, by);
    }

    /**
     * This method will add the new Todo to the TaskList and print a message
     * to the CLI, with the number of tasks now present.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        tasks.addItem(newFixDurationTask);
        ui.printAddedTask(newFixDurationTask.toString(), tasks.getNumberOfItems());
    }
}