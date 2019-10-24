package duke.logic.commands;

import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
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

    /**
     *  Creates a new LocationSearchCommand with the given location.
     *
     * @param location The location to search.
     * @throws ApiNullRequestException If the request fails.
     * @throws ApiTimeoutException If the request times out.
     */
    public LocationSearchCommand(String location) throws ApiNullRequestException, ApiTimeoutException {
        result = ApiParser.getLocationSearch(location);
    }

    /**
     * Executes this command with given param.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText("These are the coordinates of your search:\n"
                + result.getAddress() + "\n" + result.getLatitude() + " "
                + result.getLongitude());
    }
}
