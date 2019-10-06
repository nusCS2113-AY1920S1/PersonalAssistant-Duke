package duke.commands;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.UniqueTaskList;
import duke.ui.Ui;

/**
 * Class representing a command to find a task by keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param keyword The keyword to find.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        UniqueTaskList tasks = storage.getTasks();
        UniqueTaskList result = new UniqueTaskList();
        for (Task task: tasks) {
            if (task.toString().contains(keyword)) {
                result.add(task);
            }
        }
        ui.showList(result);
    }
}
