package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.TaskList;

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
