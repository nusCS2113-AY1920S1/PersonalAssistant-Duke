package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.SingaporeTravelException;
import sgtravel.logic.commands.results.CommandResult;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.profile.ProfileCard;

import java.util.HashMap;

/**
 * Shows the user the list of their favourite itinerary.
 */
public class ProfileListFavCommand extends Command {
    /**
     *  Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResult execute(Model model) throws SingaporeTravelException {
        ProfileCard profile = model.getProfileCard();
        HashMap<String, Itinerary> favHashMap = profile.getFavouriteList();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (String name : favHashMap.keySet()) {
            stringBuilder.append(i++).append(". ").append(name).append("\n");
        }
        return new CommandResultText(Messages.LIST_FAVOURITE_SUCCESS + stringBuilder.toString());
    }
}
