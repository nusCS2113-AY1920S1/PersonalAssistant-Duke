package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;

/**
 * Fetchs a location query and returns a location with coordinates.
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
     */
    @Override
    public CommandResultText execute(Model model) {
        try {
            Venue venue = ApiParser.getLocationSearch(location);
            return new CommandResultText(Messages.LOCATIONSEARCH_STARTER + venue.getAddress() + "\n"
                    + venue.getLatitude() + " " + venue.getLongitude());
        } catch (ApiException e) {
            return new CommandResultText(Messages.LOCATIONSEARCH_API_EXCEPTION);
        }
    }
}
