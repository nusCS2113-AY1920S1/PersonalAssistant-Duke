package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a Command to terminate Duke.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for ExitCommand.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Exits the Duke program.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        ui.sayBye();
    }

    /**
     * Checks if ExitCommand is called for Duke to terminate.
     *
     * @return true.
     */
    public boolean isExit() {
        return true;
    }

}