package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.storage.Storage;

/**
 * Class representing a command to add the recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {
    private Itinerary itinerary;

    public AddSampleItineraryCommand() throws DukeException {
        this.itinerary = Storage.readRecommendations();
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        // Add to the list of Itineraries
        return new CommandResultText("Successfully added this itinerary: " + "\n"
                + itinerary.printItinerary());
    }
}
