package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RecommendationsCommand;
import duke.logic.parsers.ParserUtil;
import duke.model.planning.Itinerary;

/**
 * Parses the user inputs into suitable format for RecommendationsCommand.
 */
public class RecommendationParser extends CommandParser {
    Itinerary recommendation;

    /**
     * Parses user input into recommendation.
     * @param input The User input
     */
    public RecommendationParser(String input) throws DukeException {
        recommendation = ParserUtil.createRecommendation(input);
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
