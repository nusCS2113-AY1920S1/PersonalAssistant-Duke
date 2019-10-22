package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.model.task.Deadline;
import oof.model.task.Task;

/**
 * Represents a Command that appends a new Deadline
 * object to the TaskList.
 */
public class AddDeadlineCommand extends Command {

    private String line;
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_DATE_BY = 1;

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
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split("/by");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The deadline needs a description.");
        } else if (!hasDueDate(lineSplit)) {
            throw new OofException("OOPS!!! The deadline needs a due date.");
        }
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String date = parseTimeStamp(lineSplit[INDEX_DATE_BY].trim());
        if (isDateValid(date)) {
            Task task = new Deadline(description, date);
            tasks.addTask(task);
            ui.addTaskMessage(task, tasks.getSize());
            storage.writeTaskList(tasks);
        } else {
            throw new OofException("OOPS!!! The due date is invalid.");
        }
    }

    /**
     * Checks if input has a description.
     *
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[INDEX_DESCRIPTION].trim().length() > 0;
    }

    /**
     * Checks if input has a due date.
     *
     * @param lineSplit processed user input.
     * @return true if there is a due date and due date is not whitespace.
     */
    private boolean hasDueDate(String[] lineSplit) {
        return lineSplit.length != 1 && lineSplit[INDEX_DATE_BY].trim().length() > 0;
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
