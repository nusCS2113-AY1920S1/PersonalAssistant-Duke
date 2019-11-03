package oof.command;

import java.util.ArrayList;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command that appends a new Deadline
 * object to the TaskList.
 */
public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";
    protected ArrayList<String> arguments;
    protected static final int INDEX_DESCRIPTION = 0;
    protected static final int INDEX_DATE = 1;
    protected static final int ARRAY_SIZE_DATE = 2;


    /**
     * Constructor for AddDeadlineCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddDeadlineCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
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
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storageManager      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        if (arguments.get(INDEX_DESCRIPTION).isEmpty()) {
            throw new OofException("OOPS!!! The deadline needs a description.");
        } else if (arguments.size() < ARRAY_SIZE_DATE || arguments.get(INDEX_DATE).isEmpty()) {
            throw new OofException("OOPS!!! The deadline needs a due date.");
        }
        String description = arguments.get(INDEX_DESCRIPTION);
        String date = arguments.get(INDEX_DATE);
        if (exceedsMaxLength(description)) {
            throw new OofException("Task exceeds maximum description length!");
        } else if (!isDateValid(date)) {
            throw new OofException("OOPS!!! The due date is invalid.");
        } else {
            Deadline deadline = new Deadline(description, date);
            taskList.addTask(deadline);
            ui.addTaskMessage(deadline, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }
}
