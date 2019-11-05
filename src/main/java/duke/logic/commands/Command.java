package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.model.Model;

import java.io.FileNotFoundException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command<T> {
    /**
     * Executes the command and returns the result.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback of the operation result for display.
     * @throws DukeException If an error occurs during command execution.
     */
    public abstract T execute(Model model) throws DukeException;
}
