package sgtravel.logic.commands;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoRecentItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

/**
 * Adds the given recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {
    private String newName;

    public AddSampleItineraryCommand(String word) {
        newName = word;
    }

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
        model.confirmRecentItinerary(newName);
        model.save();
        return new CommandResultText("Successfully added this itinerary: " + "\n"
                + itinerary.printItinerary());

    }
}
