package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to exit the program after the user inputs "bye".
 */
public class ExitCommand<T> extends Cmd<T>{

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(GenericList taskList, Ui ui, Storage storage) {
        System.out.println("\t Bye. Hope to see you again soon!");
    }
}
