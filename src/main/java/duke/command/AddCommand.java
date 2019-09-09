package duke.command;

import duke.commons.DukeException;
import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Represents a command that adds a Task to TaskList.
 */
public class AddCommand extends Command {

    protected Task task;

    public AddCommand(Task task) {
        super();
        this.task = task;
    }

    public void execute(TaskList tasks, Storage storage) throws DukeException {
        tasks.add(task);
        storage.serialize(tasks);
        Ui.showToUser(Message.getAddition(task, tasks));
    }
}
