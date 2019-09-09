package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


/**
 * Represents a command lists all tasks in TaskList.
 */
public class ListCommand extends Command {
    public ListCommand() {

    }

    public void execute(TaskList tasks, Storage storage, Ui ui) {
        ui.refreshTaskList(tasks, tasks);
    }
}
