package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * command.Command to list all tasks in task list
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.isEmpty()) {
                throw new DukeException("☹ OOPS!!! There are no tasks in your list");
            }
            for (int i = 0; i < tasks.size(); i++) {
                ui.addToOutput(i + 1 + ". " + tasks.get(i).toString());
            }
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }
}
