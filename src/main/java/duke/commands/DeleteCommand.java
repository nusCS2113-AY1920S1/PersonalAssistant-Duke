package duke.commands;

import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.ui.Ui;

/**
 * Class representing a command to delete a task.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public DeleteCommand(int index) {
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
            Task task = storage.getTasks().remove(index);
            if (task instanceof TaskWithDates) {
                int indexDate = 0;
                for (Task tasksWithDate: storage.getTasksWithDate()) {
                    if (task == tasksWithDate) {
                        storage.getTasksWithDate().remove(indexDate);
                        break;
                    } else {
                        indexDate++;
                    }
                }
            }
            ui.setResponse(ui.getDelete(task));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.OUT_OF_BOUNDS);
        }
        storage.write();
    }
}
