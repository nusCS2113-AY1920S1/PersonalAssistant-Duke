package command;

import commons.Message;
import commons.Ui;
import storage.Storage;
import task.TaskList;

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
