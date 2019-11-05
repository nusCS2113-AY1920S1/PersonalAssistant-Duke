package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;

/**
 * Tests the URL connection.
 */
public class LocationSearchCommand extends Command {
    private String location;

    /**
     *  Creates a new LocationSearchCommand with the given location.
     *
     * @param location The location to search.
     */
    public LocationSearchCommand(String location) {
        this.location = location;
    }

    /**
     * Executes this command with given param.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws ApiException If the Api call fails.
     */
    @Override
    public CommandResultText execute(Model model) throws ApiException {
        Venue venue = ApiParser.getLocationSearch(location);
        return new CommandResultText("These are the coordinates of your search:\n"
                + venue.getAddress() + "\n" + venue.getLatitude() + " "
                + venue.getLongitude());
    }
}
