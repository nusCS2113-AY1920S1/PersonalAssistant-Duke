package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a <code>Command</code> to list all <code>Tasks</code>
 * in the <code>TaskList</code>.
 */
public class ListCommand extends Command {
    /**
     * Constructor for <code>ListCommand</code>.
     */
    public ListCommand() {
        super();
    }

    /**
     * Lists all the <code>Tasks</code> present in the <code>TaskList</code>.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        ui.showLine();
        System.out.println("\t Here are the tasks in your list:");
        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println("\t" + (i + 1) + ". " + arr.getTask(i));
        }
        ui.showLine();
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
