package duke.logic.command.commons;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * UndoableCommand is the abstract base class for all commands that supports redo and undo operations.
 */
public interface Undoable {

    /**
     * Reverses the action of an earlier action.
     *
     * @param model A BakingList object containing application data.
     * @throws CommandException If data file is damaged.
     */
    public abstract void undo(Model model) throws CommandException;

    /**
     * Restores any actions that have been previously undone using undo.
     *
     *
     * @param model
     * @throws CommandException If data file is damaged.
     */
    public abstract void redo(Model model) throws CommandException;

}