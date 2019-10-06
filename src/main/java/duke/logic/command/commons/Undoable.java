package duke.logic.command.commons;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * API for all commands that supports redo and undo operations.
 */
public interface Undoable {

    /**
     * Reverses the action of an earlier action.
     *
     * @param model {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract void undo(Model model) throws CommandException;

    /**
     * Restores any actions that have been previously undone using undo.
     *
     * @param model {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract void redo(Model model) throws CommandException;

}