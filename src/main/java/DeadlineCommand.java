import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations required in the creation of a new Deadline.
 */

public class DeadlineCommand extends Command {
    protected Deadline newDeadline;

    /**
     * Constructor for a DeadlineCommand. Creates a new Deadline based on the input
     * provided by the user.
     * @param description The description of the Deadline.
     * @param by The date of the Deadline.
     * @throws ParseException if the date does not follow the required format
     */
    public DeadlineCommand(String description, String by) throws ParseException {
        newDeadline = new Deadline(description, by);
    }

    /**
     * This method will add the new Deadline to the TaskList and print a message
     * to the CLI, with the number of tasks now present.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        tasks.addItem(newDeadline);
        ui.printAddedTask(newDeadline.toString(), tasks.getNumberOfItems());
    }
}