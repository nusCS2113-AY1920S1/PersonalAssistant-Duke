package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.EventList;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Edits the EventList on SGTravel.
 */
public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String MESSAGE_EDIT_FAILURE = "Edit is cancelled. Changes are not saved.\n";
    private boolean canSave;
    private EventList events;

    public EditCommand(boolean canSave, EventList events) {
        this.canSave = canSave;
        this.events = events;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException {
        if (canSave && events.isUnique()) {
            model.setEvents(events);
            model.save();
            logger.log(Level.FINE, "Event list is saved.");
            return new CommandResultText(model.getEvents());
        }
        return new CommandResultText(MESSAGE_EDIT_FAILURE);
    }
}
