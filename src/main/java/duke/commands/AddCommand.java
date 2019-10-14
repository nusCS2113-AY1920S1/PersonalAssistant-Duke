package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;

/**
 * Class representing a command to add a new task.
 */
public class AddCommand extends Command {
    private final Task task;
    private static final String MESSAGE_ADDITION = "Got it. I've added this task:\n  ";

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
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        storage.getTasks().add(task);
        storage.write();
        return new CommandResult(MESSAGE_ADDITION + task);
    }
}
