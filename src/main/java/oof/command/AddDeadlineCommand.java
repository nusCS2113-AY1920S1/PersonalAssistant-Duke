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
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split("/by");

        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The deadline needs a description.");
        } else if (!hasDueDate(lineSplit)) {
            throw new OofException("OOPS!!! The deadline needs a due date.");
        }

        String description = lineSplit[0].trim();
        String date = lineSplit[1].trim();
        date = parseTimeStamp(date);
        if (isDateValid(date)) {
            Task task = new Deadline(description, date);
            arr.addTask(task);
            ui.addTaskMessage(task, arr.getSize());
            storage.writeToFile(arr);
        } else {
            throw new OofException("OOPS!!! The due date is invalid.");
        }
    }

    /**
     * Checks if input has a description.
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a due date.
     * @param lineSplit processed user input.
     * @return true if there is a due date and due date is not whitespace.
     */
    private boolean hasDueDate(String[] lineSplit) {
        return lineSplit.length != 1 && lineSplit[1].trim().length() > 0;
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
