package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DeleteCommand;
import compal.logic.parser.exceptions.ParserException;

//@@author yueyeah
/**
 * Parses input arguments and creates a new DeleteCommand object for deletion of task.
 */
public class DeleteCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        int taskID = getTaskID(restOfInput);
        return new DeleteCommand(taskID);
    }
}
