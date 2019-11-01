package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Model;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Recommends an itinerary based on number of trip days entered by user.
 */
public class RecommendationsCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String[] itineraryDetails;

    public RecommendationsCommand(String... itineraryDetails) {
        this.itineraryDetails = itineraryDetails;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());
        logger.log(Level.FINE, hotelLocation.getAddress());
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, "New Recommendation");

        List<Agenda> list = model.getRecommendations(itinerary.getNumberOfDays(), itinerary);

        assert (!list.isEmpty()) : "list should not be null";

        itinerary.setTasks(list);

        String result = itinerary.printItinerary();

        return new CommandResultText(result);
    }
}
