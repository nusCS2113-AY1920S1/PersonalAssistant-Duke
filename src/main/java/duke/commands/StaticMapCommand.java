package duke.commands;

import duke.commands.results.CommandResultImage;
import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.Model;
import duke.model.locations.Venue;
import javafx.scene.image.Image;

public class StaticMapCommand extends Command {
    private String param;
    private Image image;

    /**
     * Creates a new StaticMapCommand for the given location query.
     */
    public StaticMapCommand(String param) throws DukeException {
        this.param = param;
        Venue query = ApiParser.getLocationSearch(param);
        this.image = ApiParser.getStaticMap(ApiParser.generateStaticMapParams("512", "512", "18",
                String.valueOf(query.getLatitude()), String.valueOf(query.getLongitude()), "", "",
                ApiParser.createStaticMapPoint(String.valueOf(query.getLatitude()),
                String.valueOf(query.getLongitude()), "255", "122", "0", param)));
    }

    @Override
    public CommandResultImage execute(Model model) throws DukeException {
        return new CommandResultImage("Showing map of " + param, image);
    }
}

