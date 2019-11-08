package sgtravel.logic.commands;

import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.logic.commands.results.CommandResult;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.profile.ProfileCard;

/**
 * Shows the requested favourite Itinerary.
 */
public class ProfileShowFavCommand extends Command {
    private String name;

    /**
     * Constructs the command with the given itinerary name.
     *
     */
    public ProfileShowFavCommand(String name) {
        this.name = name;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws NoSuchItineraryException If the itinerary cannot be found.
     */
    @Override
    public CommandResult execute(Model model) throws NoSuchItineraryException {
        ProfileCard profileCard = model.getProfileCard();
        Itinerary favourite = profileCard.getFavourite(name);
        return new CommandResultText(favourite.printItinerary());
    }
}
