package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.logic.commands.results.CommandResultImage;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;
import javafx.scene.image.Image;

/**
 * Shows a map of location from StaticMap API.
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
     */
    public StaticMapCommand(String location) {
        this.param = location;
    }

    /**
     * Executes this command on the given user interface.
     *
     * @param model The Model object containing task list.
     * @return The CommandResult containing the image from StaticMap.
     */
    @Override
    public CommandResultImage execute(Model model) throws ApiException {
        Venue query = ApiParser.getLocationSearch(param);
        this.image = ApiParser.getStaticMap(ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "", "",
                ApiParser.generateStaticMapPoint(String.valueOf(query.getLatitude()),
                        String.valueOf(query.getLongitude()), RED_VALUE, GREEN_VALUE, BLUE_VALUE, param)));
        return new CommandResultImage("Showing map of " + param, image);
    }
}

