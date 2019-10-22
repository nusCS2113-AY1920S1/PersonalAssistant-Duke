package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

/**
 * Class representing a command to list items in a task list.
 */
public class NewItineraryCommand extends Command {
    private Itinerary itinerary;

    public NewItineraryCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        return new CommandResultText("New Itinerary Created");
    }
}
