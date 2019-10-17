package duke.commands;

import duke.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.TaskList;
import duke.model.events.Task;

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
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        TaskList tasks = model.getTasks();
        TaskList result = new TaskList();
        for (Task task: tasks) {
            if (task.toString().contains(keyword)) {
                result.add(task);
            }
        }
        return new CommandResultText(result);
    }
}
