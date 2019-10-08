package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Abstract class representing individual duke.commands.
 */
public abstract class Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    public abstract void execute(Ui ui, Storage storage) throws DukeException, IOException;
}
