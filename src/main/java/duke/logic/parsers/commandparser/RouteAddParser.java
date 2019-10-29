package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteAddCommand;

/**
 * Parses the user inputs into suitable format for RouteAddCommand.
 */
public class RouteAddParser extends CommandParser {
    private String[] details;

    /**
     * Parses user input into route.
     * @param input The User input
     */
    public RouteAddParser(String input) throws DukeException {
        details = input.split("desc", 2);
    }

    /**
     * Constructs RecommendationsCommand object.
     * @return RecommendationsCommand object
     */
    @Override
    public Command parse() {
        if (details.length == 2) {
            return new RouteAddCommand(details[0], details[1]);
        } else {
            return new RouteAddCommand(details[0], "");
        }
    }
}
