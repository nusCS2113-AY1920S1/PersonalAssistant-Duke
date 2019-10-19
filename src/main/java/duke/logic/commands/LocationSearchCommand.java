package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;

/**
 * Class representing a command to send the test URL connection.
 */
public class LocationSearchCommand extends Command {
    private Venue result;

    public LocationSearchCommand(String param) throws DukeException {
        result = ApiParser.getLocationSearch(param);
    }

    /**
     * Executes this command with given param.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        return new CommandResultText("These are the coordinates of your search:\n"
                + result.getAddress() + "\n" + result.getLatitude() + " "
                + result.getLongitude());
    }
}
