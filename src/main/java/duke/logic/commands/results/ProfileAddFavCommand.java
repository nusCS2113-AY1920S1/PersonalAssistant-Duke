package duke.logic.commands.results;

import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.NoSuchItineraryException;
import duke.logic.commands.Command;
import duke.model.Model;
import duke.model.planning.Itinerary;

public class ProfileAddFavCommand extends Command {
    private String name;

    /**
     * Constructs the ProfileSetParser.
     */
    public ProfileAddFavCommand(String word) {
        name = word;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws FileNotSavedException If the data could not be saved.
     * @throws NoSuchItineraryException If the itinerary cannot be found.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException, NoSuchItineraryException {
        // Add to the list of Itineraries
        Itinerary itinerary = model.getItinerary(name);
        model.addToFavourite(name, itinerary);
        model.save();
        return new CommandResultText("Successfully added this itinerary to favourite: " + "\n" + name);

    }
}
