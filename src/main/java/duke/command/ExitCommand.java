package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Represents a <code>Command</code> to terminate <code>Duke</code>.
 */
public class ExitCommand extends Command {

    /**
     * Constructor for <code>ExitCommand</code>.
     */
    public ExitCommand() {
        super();
    }

    /**
     * Exits the <code>Duke</code> program.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
        ui.sayBye();
    }

    /**
     * Checks if <code>ExitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return true.
     */
    public boolean isExit() {
        return true;
    }

}