package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DoneCommand;

import compal.logic.parser.exceptions.ParserException;

/**
 * Parses input arguments and creates a new DoneCommand object.
 */
public class DoneParser implements CommandParser {
    @Override
    public Command parseCommand(String command) throws ParserException {
        int taskID = getTaskID(command);
        return new DoneCommand(taskID);
    }
}
