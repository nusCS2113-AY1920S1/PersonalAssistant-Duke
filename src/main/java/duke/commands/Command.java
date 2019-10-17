package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.model.Model;

/**
 * Abstract class representing individual duke.commands.
 */
public abstract class Command<T> {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The Model object containing task list.
     */
    public abstract T execute(Model model) throws DukeException;
}
