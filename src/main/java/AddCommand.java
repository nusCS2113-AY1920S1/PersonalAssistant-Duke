import java.io.IOException;

/**
 * Represents a Command to add a specific {@link Task} in the {@link TaskList}
 */
public class AddCommand extends Command {

    private Task task;

    /**
     * @param task : the {@link Task} to be added in the list
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Public method used to add the task in the taskList, and write it on the hard disc
     * @param taskList the {@link TaskList} to be expanded
     * @param ui {@link Ui} used for printing the task output
     * @param storage {@link Storage} writes in the file on the hard disc
     * @throws DukeException
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
