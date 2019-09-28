package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Task;

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
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split("/by");
        if (lineSplit.length == 1) {
            throw new OofException("OOPS!!! The description of a deadline needs a due date.");
        }
        String description = lineSplit[0].trim();
        String date = lineSplit[1].trim();
        if (date.length() == 0) {
            throw new OofException("OOPS!!! The datetime of a deadline cannot be empty.");
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
     * Checks if ExitCommand is called for OofException to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
