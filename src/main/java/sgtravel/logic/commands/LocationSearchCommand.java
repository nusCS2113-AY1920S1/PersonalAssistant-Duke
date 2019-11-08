package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.logic.api.ApiParser;
import sgtravel.model.Model;
import sgtravel.model.locations.Venue;

/**
 * Fetches a location query and returns a location with coordinates.
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
