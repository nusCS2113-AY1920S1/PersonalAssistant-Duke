package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteAddCommand;

/**
 * Parses the user inputs into suitable format for RouteAddCommand.
 */
public class RouteAddParser extends CommandParser {
    String route;

    /**
     * Parses user input into route.
     * @param input The User input
     */
    public RouteAddParser(String input) throws DukeException {
        this.route = getWord(input);
    }

    /**
     * Constructs RecommendationsCommand object.
     * @return RecommendationsCommand object
     */
    @Override
    public Command parse() {
        return new RouteAddCommand(route);
    }
}
