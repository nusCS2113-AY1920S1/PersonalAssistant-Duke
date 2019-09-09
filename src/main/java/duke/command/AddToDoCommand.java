package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.Todo;

/**
 * Represents a <code>Command</code> to add <code>Todo</code> objects
 * to <code>TaskList</code>.
 */
public class AddToDoCommand extends Command {

    String line;
    /**
     * Constructor for <code>AddTodoCommand</code>.
     * @param line Command inputted by user.
     */
    public AddToDoCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the command inputted by user.
     * Adds the <code>Todo</code> object to <code>TaskList</code>
     * Stores the object in harddisk by calling <code>Storage</code> before printing the object added.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        line = line.trim();
        if (line.length() == 0) {
            throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
        }
        Task task = new Todo(line);
        arr.addTask(task);
        storage.writeToFile(arr);
        ui.addTaskMessage(task, arr.getSize());
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

