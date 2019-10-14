package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;


/**
 * Abstract class representing individual duke.commands.
 */
public abstract class Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    public abstract CommandResult execute(Storage storage) throws DukeException;
}
