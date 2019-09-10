package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a duke.command that adds a Task to TaskList.
 */
public class AddCommand extends Command implements Undoable {

    protected Task task;

    public AddCommand(Task task) {
        super();
        this.task = task;
    }

    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        tasks.add(task);
        storage.serialize(tasks);
        ui.refreshTaskList(tasks, tasks);
    }

    @Override
    public void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        tasks.remove(task);
        storage.serialize(tasks);
        ui.refreshTaskList(tasks, tasks);
    }

    @Override
    public void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException {

    }
}
