package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;

/**
 * Represents a Command to list all Tasks in the TaskList.
 */
public class ListCommand extends Command {

    /**
     * Constructor for ListCommand.
     */
    public ListCommand() {
        super();
    }

    /**
     * Lists all the Tasks present in the TaskList.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        ui.printTaskList(arr);
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
