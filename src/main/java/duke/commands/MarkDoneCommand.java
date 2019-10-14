package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import duke.data.tasks.Task;

/**
 * Class representing a command to mark a task as done.
 */
public class MarkDoneCommand extends Command {
    private int index;
    private static final String MESSAGE_MARK_DONE = "Nice! I've marked this task as done:\n  ";

    /**
     * Creates a new MarkDoneCommand with the given index.
     *
     * @param index The index of the task.
     */
    public MarkDoneCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        try {
            Task task = storage.getTasks().get(index);
            task.setDone(true);
            storage.write();
            return new CommandResult(MESSAGE_MARK_DONE + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
