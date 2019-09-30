package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;

/**
 * Represents a Command to mark Task as done.
 */
public class CompleteCommand extends Command {

    private String index;

    /**
     * Constructor for CompleteCommand.
     *
     * @param index Represents the index of the Task to be marked as done.
     */
    public CompleteCommand(String index) {
        super();
        this.index = index;
    }

    /**
     * Marks the specific Task defined by the user as done
     * after confirming the validity of the Command inputted by the user.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        try {
            int num = Integer.parseInt(index) - 1;
            if (num >= arr.getSize() || num < 0) {
                throw new OofException("OOPS!!! Invalid number!");
            } else {
                Task task = arr.getTask(num);
                task.setStatus();
                ui.completeMessage(task);
            }
        } catch (OofException e) {
            ui.printOofException(e);
        }
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
