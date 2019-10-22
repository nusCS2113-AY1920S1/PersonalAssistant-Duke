package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.Model;

public class FreeTimeCommand extends Command {
    private int duration;

    /**
     * Creates a new FreeTimeCommand.
     *
     * @param duration The number of hours of free time.
     */
    public FreeTimeCommand(int duration) {
        this.duration = duration + 1;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {

        throw new DukeException(Messages.FILE_NOT_FOUND + "Write the code!");
    }
}
