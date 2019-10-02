package duke.command;

import duke.commons.DukeException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * UndoableCommand is the abstract base class for all commands that supports redo and undo operations.
 */
public abstract class UndoableCommand extends Command {

    /**
     * Reverses the action of an earlier action.
     *
     * @param bakingList A BakingList object containing application data.
     * @param storage    A Storage object specifying the location of the data.
     * @param ui         A UI controller object.
     * @throws DukeException If data file is damaged.
     */
    public abstract void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException;

    /**
     * Restores any actions that have been previously undone using undo.
     *
     * @param bakingList A BakingList object containing application data.
     * @param storage    A Storage object specifying the location of the data.
     * @param ui         A UI controller object.
     * @throws DukeException If data file is damaged.
     */
    public abstract void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException;

}