package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.UniqueTaskList;

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
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        UniqueTaskList tasks = storage.getTasks();
        UniqueTaskList result = new UniqueTaskList();
        for (Task task: tasks) {
            if (task.toString().contains(keyword)) {
                result.add(task);
            }
        }
        return new CommandResult(result);
    }
}
