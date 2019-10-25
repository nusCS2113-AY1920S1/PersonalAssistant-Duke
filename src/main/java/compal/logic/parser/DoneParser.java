package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DoneCommand;

import compal.logic.parser.exceptions.ParserException;

/**
 * Parses input arguments and creates a new DoneCommand object.
 */
//@@author SholihinK
public class DoneParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        int taskID = getTokenTaskID(restOfInput);
        String status = getTokenStatus(restOfInput);
        return new DoneCommand(taskID,status);
    }
}
