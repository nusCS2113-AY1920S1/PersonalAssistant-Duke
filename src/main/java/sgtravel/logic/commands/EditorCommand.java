package sgtravel.logic.commands;

import sgtravel.commons.exceptions.EmptyVenueException;
import sgtravel.logic.edits.EditorManager;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.lists.EventList;

import org.apache.commons.lang3.SerializationUtils;

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
     * @throws EmptyVenueException If the event list is empty.
     */
    @Override
    public CommandResultText execute(Model model) throws EmptyVenueException {
        EventList events = SerializationUtils.clone(model.getEvents());
        EditorManager.activate(events, model.getEventVenues());
        return new CommandResultText(MESSAGE_EDITOR);
    }
}
