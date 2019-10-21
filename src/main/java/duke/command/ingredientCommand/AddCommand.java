package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a Command to add a specific {@link Task} in the {@link TaskList}.
 */
public class AddCommand extends Cmd<TaskList> {

    private Task task;

    /**
     * The constructor method for AddCommand.
     *
     * @param task : the {@link Task} to be added in the list
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Public method used to add the task in the taskList, and write it on the hard disc.
     *
     *
     * @param taskList the {@link TaskList} to be expanded
     * @param ui       {@link Ui} used for printing the task output
     * @param storage  {@link Storage} writes in the file on the hard disc
     * @throws DukeException Error while adding the command to the duke.txt file
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        taskList.addTask(task);
        ui.showAddCommand(task.toString(), taskList.size());
        try {
            storage.addCommandInFile(task.printInFile());
        } catch (IOException e) {
            throw new DukeException("Error while adding the command to the duke.txt file");
        }
    }
}
