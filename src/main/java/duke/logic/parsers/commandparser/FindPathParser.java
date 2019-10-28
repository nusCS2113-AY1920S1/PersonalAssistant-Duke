package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.FindPathCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for FindPathCommand.
 */
public class FindPathParser extends CommandParser {
    String constraint;
    int startEventIndex;
    int endEventIndex;

    /**
     * Parses user input into parameter for FindPathCommand.
     * @param input The User input
     */
    public FindPathParser(String input) throws DukeException {
        constraint = input.strip().split(" ")[1];
        startEventIndex = ParserUtil.getFirstIndex(input);
        endEventIndex = ParserUtil.getSecondIndex(input);
    }

    /**
     * Constructs FindPathCommand object.
     * @return FindPathCommand object
     */
    @Override
    public Command parse() throws DukeException {
        return new FindPathCommand(constraint, startEventIndex, endEventIndex);
    }
}
