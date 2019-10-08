package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.ui.Ui;

/**
 * Class representing a command to mark a task as done.
 */
public class MarkDoneCommand extends Command {
    private int index;

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
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        try {
            Task task = storage.getTasks().get(index);
            task.setDone(true);
            ui.showMarkDone(task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
        storage.write();
    }
}
