package duke.logic.commands;

import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.NoRecentItineraryException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

/**
 * Adds the given recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws FileNotSavedException If the data could not be saved.
     * @throws NoRecentItineraryException If there is no recent itinerary.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, NoRecentItineraryException {
        // Add to the list of Itineraries
        Itinerary itinerary = model.getRecentItinerary();
        model.confirmRecentItinerary();
        model.save();
        return new CommandResultText("Successfully added this itinerary: " + "\n"
                + itinerary.printItinerary());

    }
}
