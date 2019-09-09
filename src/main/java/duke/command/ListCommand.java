package duke.command;

import duke.commons.Message;
import duke.commons.Ui;
import duke.storage.Storage;
import duke.task.TaskList;

/**
 * Represents a command lists all tasks in TaskList.
 */
public class ListCommand extends Command {
    public ListCommand() {

    }

    public void execute(TaskList tasks, Storage storage) {
        Ui.showToUser(Message.getList(tasks));
    }
}
