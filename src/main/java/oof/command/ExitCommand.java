package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;

/**
 * Represents a Command to terminate Oof.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for ExitCommand.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Exits the Oof program.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        ui.printByeMessage();
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true.
     */
    public boolean isExit() {
        return true;
    }

}