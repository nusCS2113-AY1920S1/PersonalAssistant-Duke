package duke.logic.commands;

import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;

/**
 * Tests the URL connection.
 */
public class LocationSearchCommand extends Command {
    private Venue venue;

    /**
     *  Creates a new LocationSearchCommand with the given location.
     *
     * @param location The location to search.
     * @throws ApiNullRequestException If the request fails.
     * @throws ApiTimeoutException If the request times out.
     */
    public LocationSearchCommand(String location) throws ApiNullRequestException, ApiTimeoutException {
        venue = ApiParser.getLocationSearch(location);
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
                + venue.getAddress() + "\n" + venue.getLatitude() + " "
                + venue.getLongitude());
    }
}
