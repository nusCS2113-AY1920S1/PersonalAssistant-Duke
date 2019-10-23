package command;

import exception.DukeException;
import storage.Storage;
import task.Priority;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Adds a priority level to a certain task.
 *
 * @author Tan Yi Xiang
 * @version v1.3
 */
public class PriorityCommand extends Command {

    private int indexOfTask;
    private String priorityString;

    public PriorityCommand(int indexOfTask, String priorityString) {
        this.indexOfTask = indexOfTask;
        this.priorityString = priorityString.toLowerCase();
    }

    /**
     * Updates the priority level of a task that is not ignorable and saves the updated TaskList
     * to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {

        Priority newPriority = Priority.getPriorityLevel(priorityString);
        if (newPriority == Priority.INVALID) {
            throw new DukeException(DukeException.invalidPriorityLevel());
        }
        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        Task task = tasks.getTasks().get(indexOfTask);
        if (!task.isPrioritizable) {
            Ui.printOutput("Ignorable tasks do not have a priority level");
        } else {
            task.setPriority(newPriority);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Got it! " + task.description + " priority level is now " + priorityString);
        }
    }
}
