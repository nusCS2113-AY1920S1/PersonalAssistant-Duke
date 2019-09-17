package duke.command;

import java.io.IOException;
import java.time.LocalDateTime;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;

/**
 * Class representing a command to add a new task.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddCommand with the given task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }



    /**
     * Checks if task is duplicate and already exist in TaskList.
     *
     * @param tasks The task list.
     * @return True if task is not a duplicate.
     * @throws DukeException If task is duplicate and already exist in TaskList.
     */
    public void checkDuplicateTask(TaskList tasks) throws DukeException {
        String currentDesc = task.getDescription();
        if (task instanceof Todo) {
            Todo.checkDuplicateTodo(currentDesc, tasks);
        } else if (task instanceof Deadline) {
            LocalDateTime currentTaskDate = ((Deadline) task).getDateTime();
            Deadline.checkDuplicateDeadline(currentDesc, tasks, currentTaskDate);
        } else if (task instanceof Event) {
            LocalDateTime currentTaskDate = ((Event) task).getDateTime();
            Event.checkDuplicateEvent(currentDesc, tasks, currentTaskDate);
        }
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        checkDuplicateTask(tasks);
        Event.checkEventDateIsUnique(tasks, task);
        tasks.add(task);
        ui.printMessage("Got it. I've added this task:");
        ui.printMessage(task.toString());
        int size = tasks.size();
        ui.printMessage("You now have " + size + " tasks in the list");

        try {
            storage.writeFile(tasks.export());
        } catch (IOException e) {
            ui.printError("Error writing tasks to file");
        }
    }
}
