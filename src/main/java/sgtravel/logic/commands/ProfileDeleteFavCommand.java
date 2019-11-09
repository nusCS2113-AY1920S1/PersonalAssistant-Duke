package sgtravel.logic.commands;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Deletes itinerary from favourite.
 */
public class ProfileDeleteFavCommand extends Command {

    private final String name;

    /**
     * Constructs the ProfileSetParser.
     */
    public ProfileDeleteFavCommand(String word) {
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
        model.deleteFavourite(name);
        model.save();
        return new CommandResultText("Successfully deleted this itinerary from favourite: " + "\n" + name);
    }
}
