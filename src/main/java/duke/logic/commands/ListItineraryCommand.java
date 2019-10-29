package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

import java.io.FileNotFoundException;

/**
 * Creates a new custom itinerary.
 */
public class ListItineraryCommand extends Command {

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        return new CommandResultText("Your Saved Itineraries are :" + "\n" + model.listItineraries());
    }
}
