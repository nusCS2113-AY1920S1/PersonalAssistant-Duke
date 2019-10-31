package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;
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
    public RecommendationParser(String input) throws ParseException, ApiException {
        recommendation = createRecommendation(input);
    }

    /**
     * Parses the userInput and return a new Itinerary constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Itinerary object.
     */
    public Itinerary createRecommendation(String userInput) throws ParseException, ApiException {
        String[] itineraryDetails = userInput.substring("recommend".length()).strip().split("between| and");
        if (itineraryDetails.length == 1) {
            throw new ParseException();
        }

        if (itineraryDetails.length != 3 || itineraryDetails[1] == null || itineraryDetails[2] == null) {
            throw new ParseException();
        }

        if (itineraryDetails[0].strip().isEmpty()) {
            throw new ParseException();
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
