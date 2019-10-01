package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a duke.command that can be executed by the user.
 */
public abstract class Command {

    /**
     * Execute the command.
     *
     * @param bakingList   A BakingList.
     * @param storage A Storage object which specifies the location of the data.
     * @param ui A Ui object capable of controlling GUI.
     * @throws DukeException If the execution fails.
     */
    public abstract void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException;

}
