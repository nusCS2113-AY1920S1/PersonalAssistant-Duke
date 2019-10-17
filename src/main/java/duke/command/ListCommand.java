package duke.command;

import duke.exception.DukeException;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} used to list all the {@link Task}s in the {@link TaskList}.
 */
public class ListCommand extends Command {

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
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
