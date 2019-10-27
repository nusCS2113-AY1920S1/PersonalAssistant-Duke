package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Edits the EventList on SGTravel.
 */
public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, CorruptedFileException {
        model.save();
        logger.log(Level.FINE, "Event list is saved.");
        return new CommandResultText(model.getEvents());
    }
}
