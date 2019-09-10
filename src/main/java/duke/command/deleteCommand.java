package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;

/**
 * Represents a <code>Command</code> to delete a specific <code>Task</code>.
 */
public class deleteCommand extends Command {

    int index;

    /**
     * Constructor for <code>deleteCommand</code>.
     * @param index Represents the index of the <code>Task</code> to be deleted.
     */
    public deleteCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes the specific <code>Task</code> defined by the user
     * after confirming the validity of the command inputted by the user.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        if (index >= arr.getSize() || index < 0) {
            throw new DukeException("\u2639 OOPS!!! Invalid number!");
        }
        else {
            Task task = arr.getTask(index);
            arr.deleteTask(index);
            ui.deleteMessage(task, arr.getSize());
            storage.writeToFile(arr);
        }
    }
    /**
     * Checks if <code>exitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
