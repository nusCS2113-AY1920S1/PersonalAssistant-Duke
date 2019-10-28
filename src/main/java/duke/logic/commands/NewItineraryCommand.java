package duke.logic.commands;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.storage.Storage;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private Itinerary itinerary;

    /**
     * Constructs the command with the given sample itinerary.
     *
     */
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
        return new CommandResultText("New Itinerary Created :" + this.itinerary.printItinerary());
    }
}
