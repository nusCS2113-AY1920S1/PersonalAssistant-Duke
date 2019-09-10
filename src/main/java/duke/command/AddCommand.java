package duke.command;

import java.io.IOException;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
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
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
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
