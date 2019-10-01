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
        if (!hasDescription()) {
            throw new OofException("OOPS!!! The todo needs a description.");
        }
        Task task = new Todo(line);
        arr.addTask(task);
        storage.writeToFile(arr);
        ui.addTaskMessage(task, arr.getSize());
    }

    private boolean hasDescription() {
        return line.trim().length() != 0;
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

