package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResult;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.model.profile.ProfileCard;

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
    public CommandResult execute(Model model) throws DukeException {
        ProfileCard profile = model.getProfileCard();
        HashMap<String, Itinerary> favHashMap = profile.getFavouriteList();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (String name : favHashMap.keySet()) {
            stringBuilder.append(i++).append(". ").append(name).append("\n");
        }
        return new CommandResultText("Here is a list of favourite itinerary: " + "\n" + stringBuilder.toString());
    }
}
