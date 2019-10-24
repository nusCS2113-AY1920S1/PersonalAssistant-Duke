package duke.logic.commands;

import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.logic.commands.results.CommandResultImage;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;
import javafx.scene.image.Image;

/**
 * Class representing a command to fetch the image of a queried location.
 */
public class StaticMapCommand extends Command {
    private static final String DIMENSIONS = "512";
    private static final String ZOOM_LEVEL = "18";
    private static final String RED_VALUE = "255";
    private static final String GREEN_VALUE = "122";
    private static final String BLUE_VALUE = "0";

    private String param;
    private Image image;

    /**
     * Creates a new StaticMapCommand for the given location query.
     *
     * @param location The location to query.
     * @throws ApiNullRequestException If request fails.
     * @throws ApiTimeoutException If request times out.
     */
    public StaticMapCommand(String location) throws ApiNullRequestException, ApiTimeoutException,
            ApiFailedRequestException {
        this.param = location;
        Venue query = ApiParser.getLocationSearch(location);
        this.image = ApiParser.getStaticMap(ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "", "",
                ApiParser.createStaticMapPoint(String.valueOf(query.getLatitude()),
                String.valueOf(query.getLongitude()), RED_VALUE, GREEN_VALUE, BLUE_VALUE, location)));
    }

    /**
     * Executes this command on the given user interface.
     *
     * @param model The Model object containing task list.
     * @return The CommandResult containing the image from StaticMap.
     * @throws ApiNullRequestException If request fails.
     * @throws ApiTimeoutException If request times out.
     */
    @Override
    public CommandResultImage execute(Model model) throws ApiNullRequestException, ApiTimeoutException {
        return new CommandResultImage("Showing map of " + param, image);
    }
}

