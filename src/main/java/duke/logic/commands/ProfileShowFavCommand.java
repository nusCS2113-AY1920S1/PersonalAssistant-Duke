package duke.logic.commands;

import duke.commons.exceptions.NoSuchItineraryException;
import duke.logic.commands.results.CommandResult;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;

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
