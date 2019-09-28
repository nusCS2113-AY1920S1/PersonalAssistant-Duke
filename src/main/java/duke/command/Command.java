package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * A command that is executable by the user.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param bakingList A BakingList object containing application data.
     * @param storage A Storage object specifying the location of the data.
     * @param ui A UI controller object.
     * @throws DukeException If the execution fails.
     */
    public abstract void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException;
}
