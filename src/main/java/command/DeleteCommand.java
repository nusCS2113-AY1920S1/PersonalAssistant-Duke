package command;

import commons.DukeException;
import commons.Message;
import commons.Ui;
import storage.Storage;
import task.TaskList;

/**
 * Represents a command that deletes a Task from TaskList.
 */
public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index - 1;
    }

    public void execute(TaskList tasks, Storage storage) throws DukeException {
        try {
            String message = Message.getDeletion(tasks.get(index), tasks);
            tasks.remove(index);
            storage.serialize(tasks);
            Ui.showToUser(message);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please provide valid index");
        }
    }
}
