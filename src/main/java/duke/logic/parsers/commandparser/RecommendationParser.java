package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.api.ApiParser;
import duke.logic.commands.Command;
import duke.logic.commands.RecommendationsCommand;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;

import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 * Parses the user inputs into suitable format for RecommendationsCommand.
 */
public class RecommendationParser extends CommandParser {
    private Itinerary recommendation;

    /**
     * Parses user input into recommendation.
     * @param input The User input
     */
    public RecommendationParser(String input) throws DukeException {
        recommendation = createRecommendation(input);
    }

    /**
     * Parses the userInput and return a new Itinerary constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Itinerary object.
     */
    public Itinerary createRecommendation(String userInput) throws DukeException {
        String[] itineraryDetails = userInput.substring("recommend".length()).strip().split("between| and");
        if (itineraryDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }

        if (itineraryDetails.length != 3 || itineraryDetails[1] == null || itineraryDetails[2] == null) {
            throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        if (itineraryDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.ERROR_DESCRIPTION_EMPTY);
        }

        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());
        logger.log(Level.FINE, hotelLocation.getAddress());
        return new Itinerary(start, end, hotelLocation, "New Recommendation");
    }

    /**
     * Constructs RecommendationsCommand object.
     * @return RecommendationsCommand object
     */
    @Override
    public Command parse() {
        return new RecommendationsCommand(recommendation);
    }
}
