package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RecommendationsCommand;

/**
 * Parses the user inputs into suitable format for RecommendationsCommand.
 */
public class RecommendationParser extends CommandParser {
    private String[] itineraryDetails;

    /**
     * Parses user input into recommendation.
     * @param input The User input
     */
    public RecommendationParser(String input) throws ParseException {
        itineraryDetails = createRecommendation(input);
    }

    /**
     * Parses the userInput and return a new Itinerary constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Itinerary object.
     */
    private String[] createRecommendation(String userInput) throws ParseException {
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
        return itineraryDetails;
    }

    /**
     * Constructs RecommendationsCommand object.
     * @return RecommendationsCommand object
     */
    @Override
    public Command parse() {
        return new RecommendationsCommand(itineraryDetails);
    }
}
