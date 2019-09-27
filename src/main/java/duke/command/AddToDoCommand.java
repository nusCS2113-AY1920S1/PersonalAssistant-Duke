package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.Todo;

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
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        line = line.trim();
        if (line.length() == 0) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        Task task = new Todo(line);
        arr.addTask(task);
        storage.writeToFile(arr);
        ui.addTaskMessage(task, arr.getSize());
    }

    /**
     * Checks if ExitCommand is called for Duke to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}

