package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;


/**
 * Represents a duke.command that deletes a Task from TaskList.
 */
public class DeleteCommand extends UndoableCommand {
    private int index;
    private Task deleted;
    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        try {
            String message = Message.getDeletion(tasks.get(index), tasks);
            deleted = tasks.get(index);
            tasks.remove(index);
            storage.serialize(tasks);
            ui.refreshTaskList(tasks, tasks);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please provide valid index");
        }
    }

    public void undo(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        tasks.add(deleted, index);
        storage.serialize(tasks);
        ui.refreshTaskList(tasks, tasks);
    }

    public void redo(TaskList tasks, Storage storage, Ui ui) throws DukeException {

    }
}
