package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


/**
 * Represents a done command that set a Task to done.
 */
public class DoneCommand extends UndoableCommand {

    protected int index;

    public DoneCommand(int index) {
        this.index = index - 1;
    }

    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        try {
            tasks.get(index).setDone(true);
            storage.serialize(tasks);
            ui.refreshTaskList(tasks, tasks);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Index out of bound.");
        }

    }

    public void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        tasks.get(index).setDone(false);
        storage.serialize(tasks);
        ui.refreshTaskList(tasks, tasks);
    }

    public void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException {

    }
}