package duke.logic.commands;

import duke.commons.exceptions.EmptyVenueException;
import duke.logic.EditorManager;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Turns on the editing mode on SGTravel.
 */
public class EditorCommand extends Command {
    private static final String MESSAGE_EDITOR = "Editor mode is turned on. Please press any key to begin. "
            + "Enter new information to edit. Enter x to save changes and exit Editor mode.";

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws EmptyVenueException {
        EditorManager.activate(model.getEvents(), model.getEventVenues());
        return new CommandResultText(MESSAGE_EDITOR);
    }
}
