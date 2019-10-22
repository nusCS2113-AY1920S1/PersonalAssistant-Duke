package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to list all the {@link Task}s in the {@link TaskList}.
 */
public class ListCommand extends Cmd<TaskList> {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (taskList.size() == 0) {
            throw new DukeException("No tasks yet!");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for (int i = 1; i <= taskList.size(); i++) { // looping to print all the saved tasks
                ui.showTask("\t " + i + "." + taskList.getTask(i - 1).toString());
            }
        }
    }
}
