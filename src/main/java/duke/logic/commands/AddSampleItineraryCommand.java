package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

import java.io.FileNotFoundException;

/**
 * Adds the given recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {
    private Itinerary itinerary;

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        // Add to the list of Itineraries
        itinerary = model.getRecentItinerary();
        model.confirmRecentItinerary();
        model.save();
        return new CommandResultText("Successfully added this itinerary: " + "\n"
                + itinerary.printItinerary());

    }
}
