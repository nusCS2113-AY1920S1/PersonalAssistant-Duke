package duke.command;

import duke.command.ingredientCommand.ExitCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public abstract class Cmd<T> {
    public abstract void execute(GenericList<T> tasklist, Ui ui, Storage storage) throws DukeException;
    /**
     * Returns the boolean indicating that it is( not) an {@link ExitCommand}.
     *
     * @return false by default
     */
    public boolean isExit() {
        return false;
    }
}
