package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to exit the program after the user inputs "bye".
 */
public class ExitCommand extends Cmd<TaskList>{

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        System.out.println("\t Bye. Hope to see you again soon!");
    }
}
