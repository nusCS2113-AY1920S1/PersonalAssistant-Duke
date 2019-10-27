package duke.logic.commands;

import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.lists.EventList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, CorruptedFileException {
        model.save();
        logger.log(Level.INFO, "Event list is saved.");
        return new CommandResultText(model.getEvents());
    }
}
