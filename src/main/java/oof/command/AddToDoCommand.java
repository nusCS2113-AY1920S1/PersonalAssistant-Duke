package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;
import oof.task.Todo;

/**
 * Represents a Command to add Todo objects to TaskList.
 */
public class AddToDoCommand extends Command {

    private String line;
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_DATE_ON = 1;

    /**
     * Constructor for AddTodoCommand.
     *
     * @param line Command inputted by user.
     */
    public AddToDoCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the Command inputted by user.
     * Adds the Todo object to TaskList
     * Stores the object in hard disk by calling Storage before printing the object added.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split("/on");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The todo needs a description.");
        } else if (!hasOnDate(lineSplit)) {
            throw new OofException("OOPS!!! The todo needs a date.");
        }
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String onDate = parseTimeStamp(lineSplit[INDEX_DATE_ON].trim());
        if (isDateValid(onDate)) {
            Task task = new Todo(description, onDate);
            arr.addTask(task);
            storage.writeToFile(arr);
            ui.addTaskMessage(task, arr.getSize());
        } else {
            throw new OofException("OOPS!!! The date is invalid.");
        }
    }

    /**
     * Checks if input has a description.
     *
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a date.
     *
     * @param lineSplit processed user input.
     * @return true if there is a date and date is not whitespace.
     */
    private boolean hasOnDate(String[] lineSplit) {
        return lineSplit.length != 1 && lineSplit[INDEX_DATE_ON].trim().length() > 0;
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}

