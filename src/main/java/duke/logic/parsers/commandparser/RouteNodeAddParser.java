package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeEmptyFieldException;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeAddCommand;
import duke.logic.parsers.ParserUtil;
import duke.model.locations.RouteNode;

/**
 * Parses the user inputs into suitable format for RouteNodeAddCommand.
 */
public class RouteNodeAddParser extends CommandParser {
    String input;
    RouteNode routeNode;
    int firstIndex;

    /**
     * Parses user input into parameter for RouteNodeAddCommand.
     * @param input The User input
     */
    public RouteNodeAddParser(String input) throws DukeException {
        this.input = input;
        routeNode = ParserUtil.createRouteNode(input);
        firstIndex = ParserUtil.getFirstIndex(input);
    }

    /**
     * Creates a new RouteNodeAddCommand from input, factoring for empty indexNode field.
     * @return RouteNodeAddCommand The command.
     */
    @Override
    public Command parse() throws DukeException {
        try {
            return new RouteNodeAddCommand(routeNode,
                    firstIndex, ParserUtil.getSecondIndex(input), false);
        } catch (DukeEmptyFieldException e) {
            return new RouteNodeAddCommand(routeNode,
                    firstIndex, 0, true);
        }
    }
}
