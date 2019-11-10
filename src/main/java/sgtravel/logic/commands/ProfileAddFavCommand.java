package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

/**
 * Adds the favourite itinerary to profile.
 */
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
        Itinerary itinerary = model.getItinerary(name);
        model.addToFavourite(name, itinerary);
        model.save();
        return new CommandResultText(Messages.ADD_FAVOURITE_SUCCESS + name);
    }
}
