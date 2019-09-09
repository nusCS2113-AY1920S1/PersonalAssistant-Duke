package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.TaskList;

/**
 * Represents a done command that set a Task to done.
 */
public class DoneCommand extends Command {

    protected int index;

    public DoneCommand(int index) {
        this.index = index - 1;
    }

    public void execute(TaskList tasks, Storage storage) throws DukeException {
        try {
            tasks.get(index).setDone(true);
            storage.serialize(tasks);
            Ui.showToUser(Message.getDone(tasks.get(index)));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Index out of bound.");
        }

    }
}