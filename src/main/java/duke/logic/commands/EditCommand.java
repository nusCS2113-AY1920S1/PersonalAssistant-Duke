package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Event;
import duke.model.Model;
import duke.model.lists.EventList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private EventList events;

    public EditCommand(EventList events) {
        this.events = events;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) {
        for (Event e : events) {
            logger.log(Level.INFO, e.toString());
        }
        return new CommandResultText(model.getEvents());
    }
}
