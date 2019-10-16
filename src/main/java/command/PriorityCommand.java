package command;

import exception.DukeException;
import storage.Storage;
import task.Priority;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * The PriorityCommand class is used when the user intends to add a priority level
 * to a certain list item.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class PriorityCommand extends Command {

    private int indexOfTask;
    private String priorityString;

    public PriorityCommand(int indexOfTask, String priorityString) {
        this.indexOfTask = indexOfTask;
        this.priorityString = priorityString.toLowerCase();
    }

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
            Ui.printOutput("Ignorable tasks can't have a priority level");
        } else {
            task.setPriority(newPriority);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Got it! " + task.description + " priority level is now " + priorityString);
        }
    }
}
