package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Task;

/**
 * Represents a <code>Command</code> that appends a new <code>Deadline</code>
 * object to the <code>TaskList</code>.
 */
public class AddDeadlineCommand extends Command {

    String line;

    /**
     * Constructor for <code>AddDeadlineCommand</code>.
     * @param line Command inputted by user for processing.
     */
    public AddDeadlineCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the Command inputted by user into <code>description</code> and <code>date</code>.
     * Checks for the validity of the format of <code>date</code>.
     * Adds a <code>Deadline</code> object to the <code>TaskList</code>
     * and prints the object added before calling methods in <code>Storage</code> to
     * store the object added in the harddisk.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *                objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        String[] linesplit = line.split("/by");
        if (linesplit.length == 1) {
            throw new DukeException("OOPS!!! The description of a deadline needs a due date.");
        }
        String description = linesplit[0].trim();
        String date = linesplit[1].trim();
        if (date.length() == 0) {
            throw new DukeException("OOPS!!! The datetime of a deadline cannot be empty.");
        } else {
            date = parseTimeStamp(date);
            if (date != "failed") {
                Task task = new Deadline(description, date);
                arr.addTask(task);
                ui.addTaskMessage(task, arr.getSize());
                storage.writeToFile(arr);
            }
        }
    }

    /**
     * Checks if <code>ExitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
