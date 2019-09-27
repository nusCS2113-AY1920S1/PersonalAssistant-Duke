package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Task;

/**
 * Represents a Command that appends a new Deadline
 * object to the TaskList.
 */
public class AddDeadlineCommand extends Command {

    private String line;

    /**
     * Constructor for AddDeadlineCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddDeadlineCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the Command inputted by user into description and date.
     * Checks for the validity of the format of date.
     * Adds a Deadline object to the TaskList
     * and prints the object added before calling methods in Storage to
     * store the object added in the hard disk.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        String[] lineSplit = line.split("/by");
        if (lineSplit.length == 1) {
            throw new DukeException("OOPS!!! The description of a deadline needs a due date.");
        }
        String description = lineSplit[0].trim();
        String date = lineSplit[1].trim();
        if (date.length() == 0) {
            throw new DukeException("OOPS!!! The datetime of a deadline cannot be empty.");
        } else {
            date = parseTimeStamp(date);
            if (!date.equals("failed")) {
                Task task = new Deadline(description, date);
                arr.addTask(task);
                ui.addTaskMessage(task, arr.getSize());
                storage.writeToFile(arr);
            }
        }
    }

    /**
     * Checks if ExitCommand is called for Duke to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
