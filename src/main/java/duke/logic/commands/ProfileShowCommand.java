package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Shows the user profile.
 */
public class ProfileShowCommand extends Command {
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(model.getProfileCard());
    }
}
