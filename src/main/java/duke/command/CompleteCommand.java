package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;

/**
 * Represents a <code>Command</code> to mark <code>Task</code> as done.
 */
public class CompleteCommand extends Command {

    String index;

    /**
     * Constructor for <code>CompleteCommand</code>.
     * @param index Represents the index of the <code>Task</code> to be marked as done.
     */
    public CompleteCommand(String index) {
        super();
        this.index = index;
    }

    /**
     * Marks the specific <code>Task</code> defined by the user as done
     * after confirming the validity of the Command inputted by the user.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        try {
            int num = Integer.parseInt(index) - 1;
            if (num >= arr.getSize() || num < 0) {
                throw new DukeException("\u2639 OOPS!!! Invalid number!");
            } else {
                Task task = arr.getTask(num);
                task.setStatus();
                ui.completeMessage(task);
            }
        } catch (Exception e) {
            System.out.println("\u2639 OOPS!!! Invalid number!");
        }
    }

    /**
     * Checks if <code>ExitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
